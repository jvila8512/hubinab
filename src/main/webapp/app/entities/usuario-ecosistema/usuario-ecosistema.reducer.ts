import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending, isRejected } from '@reduxjs/toolkit';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { IUsuarioEcosistema, defaultValue } from 'app/shared/model/usuario-ecosistema.model';

const initialState: EntityState<IUsuarioEcosistema> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

const apiUrl = 'api/usuario-ecosistemas';

// Actions

export const getEntities = createAsyncThunk('usuarioEcosistema/fetch_entity_list', async ({ page, size, sort }: IQueryParams) => {
  const requestUrl = `${apiUrl}?cacheBuster=${new Date().getTime()}`;
  return axios.get<IUsuarioEcosistema[]>(requestUrl);
});

export const getEntity = createAsyncThunk(
  'usuarioEcosistema/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<IUsuarioEcosistema>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

export const getEntityByUserId = createAsyncThunk(
  'usuarioEcosistema/fetch_entityByUserId',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/user-id/${id}`;
    return axios.get<IUsuarioEcosistema>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

export const createEntity = createAsyncThunk(
  'usuarioEcosistema/create_entity',
  async (entity: IUsuarioEcosistema, thunkAPI) => {
    const result = await axios.post<IUsuarioEcosistema>(apiUrl, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const updateEntity = createAsyncThunk(
  'usuarioEcosistema/update_entity',
  async (entity: IUsuarioEcosistema, thunkAPI) => {
    const result = await axios.put<IUsuarioEcosistema>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);
export const updateEntity1 = createAsyncThunk(
  'usuarioEcosistema/update_entity',
  async (entity: IUsuarioEcosistema, thunkAPI) => {
    const result = await axios.put<IUsuarioEcosistema>(`api/usuario-ecosistemas1/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const partialUpdateEntity = createAsyncThunk(
  'usuarioEcosistema/partial_update_entity',
  async (entity: IUsuarioEcosistema, thunkAPI) => {
    const result = await axios.patch<IUsuarioEcosistema>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const deleteEntity = createAsyncThunk(
  'usuarioEcosistema/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    const result = await axios.delete<IUsuarioEcosistema>(requestUrl);
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

// slice

export const UsuarioEcosistemaSlice = createEntitySlice({
  name: 'usuarioEcosistema',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getEntity.fulfilled, (state, action) => {
        state.loading = false;
        state.entity = action.payload.data;
      })
      .addCase(getEntityByUserId.fulfilled, (state, action) => {
        state.loading = false;
        state.entity = action.payload.data;
      })
      .addCase(deleteEntity.fulfilled, state => {
        state.updating = false;
        state.updateSuccess = true;
        state.entity = {};
      })
      .addMatcher(isFulfilled(getEntities), (state, action) => {
        const { data } = action.payload;

        return {
          ...state,
          loading: false,
          entities: data,
        };
      })

      .addMatcher(isFulfilled(createEntity, updateEntity, partialUpdateEntity, updateEntity1), (state, action) => {
        state.updating = false;
        state.loading = false;
        state.updateSuccess = true;
        state.entity = action.payload.data;
      })
      .addMatcher(isPending(getEntities, getEntity, getEntityByUserId), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isPending(createEntity, updateEntity, updateEntity1, partialUpdateEntity, deleteEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.updating = true;
      });
  },
});

export const { reset } = UsuarioEcosistemaSlice.actions;

// Reducer
export default UsuarioEcosistemaSlice.reducer;
