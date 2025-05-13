import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending, isRejected } from '@reduxjs/toolkit';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { IIdea, defaultValue } from 'app/shared/model/idea.model';

const initialState: EntityState<IIdea> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

const apiUrl = 'api/ideas';

// Actions

export const getEntities = createAsyncThunk('idea/fetch_entity_list', async ({ page, size, sort }: IQueryParams) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}&` : '?'}cacheBuster=${new Date().getTime()}`;

  return axios.get<IIdea[]>(requestUrl);
});

export const getEntitiesbyRetoId = createAsyncThunk('idea/fetch_entity_list_by_Retoid', async ({ id, iduser }: IQueryParams) => {
  const requestUrl = `${apiUrl}/ideasbyretoid/${id}/${iduser}`;
  return axios.get<IIdea[]>(requestUrl);
});

export const getEntitiesbyReto = createAsyncThunk('idea/fetch_entity_list_by_Reto_Solo', async (id: string | number) => {
  const requestUrl = `${apiUrl}/ideasbyretosolo/${id}`;
  return axios.get<IIdea[]>(requestUrl);
});

export const getEntity = createAsyncThunk(
  'idea/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<IIdea>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

export const createEntity = createAsyncThunk(
  'idea/create_entity',
  async (entity: IIdea, thunkAPI) => {
    const result = await axios.post<IIdea>(apiUrl, cleanEntity(entity));

    return result;
  },
  { serializeError: serializeAxiosError }
);

export const updateEntity = createAsyncThunk(
  'idea/update_entity',
  async (entity: IIdea, thunkAPI) => {
    const result = await axios.put<IIdea>(`${apiUrl}/${entity.id}`, cleanEntity(entity));

    return result;
  },
  { serializeError: serializeAxiosError }
);
export const updateEntitySinRespuesta = createAsyncThunk(
  'idea/update_entity_sin_respuesta',
  async (id: string | number) => {
    const result = await axios.put(`${apiUrl}/sinrespuesta/${id}`);

    return result;
  },
  { serializeError: serializeAxiosError }
);

export const partialUpdateEntity = createAsyncThunk(
  'idea/partial_update_entity',
  async (entity: IIdea, thunkAPI) => {
    const result = await axios.patch<IIdea>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const deleteEntity = createAsyncThunk(
  'idea/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    const result = await axios.delete<IIdea>(requestUrl);

    return result;
  },
  { serializeError: serializeAxiosError }
);

// slice

export const IdeaSlice = createEntitySlice({
  name: 'idea',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getEntity.fulfilled, (state, action) => {
        state.loading = false;
        state.entity = action.payload.data;
      })
      .addCase(deleteEntity.fulfilled, state => {
        state.updating = false;
        state.updateSuccess = true;
        state.entity = {};
      })
      .addMatcher(isFulfilled(getEntities, getEntitiesbyRetoId, getEntitiesbyReto), (state, action) => {
        const { data, headers } = action.payload;

        return {
          ...state,
          loading: false,
          entities: data,
          totalItems: parseInt(headers['x-total-count'], 10),
        };
      })
      .addMatcher(isFulfilled(createEntity, updateEntitySinRespuesta, updateEntity, partialUpdateEntity), (state, action) => {
        state.updating = false;
        state.loading = false;
        state.updateSuccess = true;
        state.entity = action.payload.data;
      })
      .addMatcher(isPending(getEntities, getEntity, getEntitiesbyRetoId, getEntitiesbyReto), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isPending(createEntity, updateEntitySinRespuesta, updateEntity, partialUpdateEntity, deleteEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.updating = true;
      });
  },
});

export const { reset } = IdeaSlice.actions;

// Reducer
export default IdeaSlice.reducer;
