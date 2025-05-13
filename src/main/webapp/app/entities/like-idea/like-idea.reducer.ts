import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending, isRejected } from '@reduxjs/toolkit';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { ILikeIdea, defaultValue } from 'app/shared/model/like-idea.model';

const initialState: EntityState<ILikeIdea> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

const apiUrl = 'api/like-ideas';

// Actions

export const getEntities = createAsyncThunk('likeIdea/fetch_entity_list', async ({ page, size, sort }: IQueryParams) => {
  const requestUrl = `${apiUrl}?cacheBuster=${new Date().getTime()}`;
  return axios.get<ILikeIdea[]>(requestUrl);
});
export const getEntitiesByIdea = createAsyncThunk('likeIdea/fetch_entity_list_by_Idea', async (id: string | number) => {
  const requestUrl = `${apiUrl}/byIdea/${id}`;
  return axios.get<ILikeIdea[]>(requestUrl);
});

export const getEntity = createAsyncThunk(
  'likeIdea/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<ILikeIdea>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

export const createEntity = createAsyncThunk(
  'likeIdea/create_entity',
  async (entity: ILikeIdea, thunkAPI) => {
    const result = await axios.post<ILikeIdea>(apiUrl, cleanEntity(entity));

    return result;
  },
  { serializeError: serializeAxiosError }
);

export const updateEntity = createAsyncThunk(
  'likeIdea/update_entity',
  async (entity: ILikeIdea, thunkAPI) => {
    const result = await axios.put<ILikeIdea>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const partialUpdateEntity = createAsyncThunk(
  'likeIdea/partial_update_entity',
  async (entity: ILikeIdea, thunkAPI) => {
    const result = await axios.patch<ILikeIdea>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const deleteEntity = createAsyncThunk(
  'likeIdea/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    const result = await axios.delete<ILikeIdea>(requestUrl);

    return result;
  },
  { serializeError: serializeAxiosError }
);

// slice

export const LikeIdeaSlice = createEntitySlice({
  name: 'likeIdea',
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
      .addMatcher(isFulfilled(getEntities, getEntitiesByIdea), (state, action) => {
        const { data } = action.payload;

        return {
          ...state,
          loading: false,
          entities: data,
        };
      })
      .addMatcher(isFulfilled(createEntity, updateEntity, partialUpdateEntity), (state, action) => {
        state.updating = false;
        state.loading = false;
        state.updateSuccess = true;
        state.entity = action.payload.data;
      })
      .addMatcher(isPending(getEntities, getEntity, getEntitiesByIdea), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isPending(createEntity, updateEntity, partialUpdateEntity, deleteEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.updating = true;
      });
  },
});

export const { reset } = LikeIdeaSlice.actions;

// Reducer
export default LikeIdeaSlice.reducer;
