import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending, isRejected } from '@reduxjs/toolkit';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { IEcosistemaComponente, defaultValue } from 'app/shared/model/ecosistema-componente.model';

const initialState: EntityState<IEcosistemaComponente> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

const apiUrl = 'api/ecosistema-componentes';

// Actions

export const getEntities = createAsyncThunk('ecosistemaComponente/fetch_entity_list', async ({ page, size, sort }: IQueryParams) => {
  const requestUrl = `${apiUrl}?cacheBuster=${new Date().getTime()}`;
  return axios.get<IEcosistemaComponente[]>(requestUrl);
});
export const getComponentesbyEcosistema = createAsyncThunk(
  'ecosistemaComponente/fetch_entity_list_componentes_by_ecosistema',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/componentebyecosistema/${id}`;
    return axios.get<IEcosistemaComponente[]>(requestUrl);
  }
);

export const getEntity = createAsyncThunk(
  'ecosistemaComponente/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<IEcosistemaComponente>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

export const createEntity = createAsyncThunk(
  'ecosistemaComponente/create_entity',
  async (entity: IEcosistemaComponente, thunkAPI) => {
    const result = await axios.post<IEcosistemaComponente>(apiUrl, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const updateEntity = createAsyncThunk(
  'ecosistemaComponente/update_entity',
  async (entity: IEcosistemaComponente, thunkAPI) => {
    const result = await axios.put<IEcosistemaComponente>(`${apiUrl}/${entity.id}`, cleanEntity(entity));

    return result;
  },
  { serializeError: serializeAxiosError }
);

export const partialUpdateEntity = createAsyncThunk(
  'ecosistemaComponente/partial_update_entity',
  async (entity: IEcosistemaComponente, thunkAPI) => {
    const result = await axios.patch<IEcosistemaComponente>(`${apiUrl}/${entity.id}`, cleanEntity(entity));

    return result;
  },
  { serializeError: serializeAxiosError }
);

export const deleteEntity = createAsyncThunk(
  'ecosistemaComponente/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    const result = await axios.delete<IEcosistemaComponente>(requestUrl);

    return result;
  },
  { serializeError: serializeAxiosError }
);

// slice

export const EcosistemaComponenteSlice = createEntitySlice({
  name: 'ecosistemaComponente',
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
      .addMatcher(isFulfilled(getEntities, getComponentesbyEcosistema), (state, action) => {
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
      .addMatcher(isPending(getEntities, getEntity, getComponentesbyEcosistema), state => {
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

export const { reset } = EcosistemaComponenteSlice.actions;

// Reducer
export default EcosistemaComponenteSlice.reducer;
