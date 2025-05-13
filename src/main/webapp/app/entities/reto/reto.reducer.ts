import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending, isRejected } from '@reduxjs/toolkit';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { IReto, defaultValue } from 'app/shared/model/reto.model';

const initialState: EntityState<IReto> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

const apiUrl = 'api/retos';

// Actions

export const getEntities = createAsyncThunk('reto/fetch_entity_list', async ({ page, size, sort }: IQueryParams) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}&` : '?'}cacheBuster=${new Date().getTime()}`;
  return axios.get<IReto[]>(requestUrl);
});

export const getEntitiesByEcosistema1 = createAsyncThunk(
  'reto/fetch_entity_list_By_ecosistema_Paginado',
  async ({ id, page, size, sort }: IQueryParams) => {
    const requestUrl = `${apiUrl}/ecosistemaId1/${id}${`?page=${page}&size=${size}&sort=${sort}`}`;
    return axios.get<IReto[]>(requestUrl);
  }
);
export const getEntitiesByEcosistema = createAsyncThunk('reto/fetch_entity_list_By_ecosistema_EStaNo', async (id: string | number) => {
  const requestUrl = `${apiUrl}/ecosistemaId/${id}`;
  return axios.get<IReto[]>(requestUrl);
});
export const getEntitiesByEcosistematodasFiltrarFechas = createAsyncThunk(
  'reto/fetch_entity_list_By_ecosistema_Fecas_Filtradas',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/ecosistemaIdRevisarFechas/${id}`;
    return axios.get<IReto[]>(requestUrl);
  }
);

export const getEntitiesByEcosistematodasFiltrarFechasSinRespuesta = async id => {
  const requestUrl = `${apiUrl}/ecosistemaIdRevisarFechasSinRespuesta/${id}`;
  return axios.get(requestUrl);
};

export const getEntitiesByEcosistemaIdbyActivoDeTodoslosEcosistemasUSerLogueado = createAsyncThunk(
  `${apiUrl}/retosTodosByEcosistemasId111111`,
  async (idecosistemas: []) => {
    const requestUrl = `${apiUrl}/retosTodosByEcosistemasId/${idecosistemas}`;
    return axios.get<IReto[]>(requestUrl);
  }
);

export const getEntitiesByEcosistemabyIdUser = createAsyncThunk(
  'reto/fetch_entity_list_By_ecosistemaByIdUser',
  async ({ id, iduser }: IQueryParams) => {
    const requestUrl = `${apiUrl}/ecosistemaId/${id}/${iduser}`;
    return axios.get<IReto[]>(requestUrl);
  }
);
export const contarRetosbyEcosistemas = async id => {
  const requestUrl = `${apiUrl}/contarEcosistemas/${id}`;
  return axios.get(requestUrl);
};

export const getEntity = createAsyncThunk(
  'reto/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<IReto>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);
// thunkAPI.dispatch(getEntities({}));
export const createEntity = createAsyncThunk(
  'reto/create_entity',
  async (entity: IReto, thunkAPI) => {
    const result = await axios.post<IReto>(apiUrl, cleanEntity(entity));

    return result;
  },
  { serializeError: serializeAxiosError }
);

export const updateEntity = createAsyncThunk(
  'reto/update_entity',
  async (entity: IReto, thunkAPI) => {
    const result = await axios.put<IReto>(`${apiUrl}/${entity.id}`, cleanEntity(entity));

    return result;
  },
  { serializeError: serializeAxiosError }
);
export const updateEntitysinRespuesta = createAsyncThunk(
  'reto/update_entity_Sin?respuesta',
  async (id: string | number) => {
    const result = await axios.put(`${apiUrl}/sinrespuesta/${id}`);

    return result;
  },
  { serializeError: serializeAxiosError }
);

export const updateEntityFechasFiltradas = createAsyncThunk(
  'reto/update_entity',
  async (entity: IReto, thunkAPI) => {
    const result = await axios.put<IReto>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntitiesByEcosistematodasFiltrarFechas(entity.ecosistema.id));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const partialUpdateEntity = createAsyncThunk(
  'reto/partial_update_entity',
  async (entity: IReto, thunkAPI) => {
    const result = await axios.patch<IReto>(`${apiUrl}/${entity.id}`, cleanEntity(entity));

    return result;
  },
  { serializeError: serializeAxiosError }
);

export const deleteEntity = createAsyncThunk(
  'reto/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    const result = await axios.delete<IReto>(requestUrl);
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

// slice

export const RetoSlice = createEntitySlice({
  name: 'reto',
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
      .addMatcher(
        isFulfilled(
          getEntities,
          getEntitiesByEcosistema,
          getEntitiesByEcosistema1,
          getEntitiesByEcosistemabyIdUser,
          getEntitiesByEcosistematodasFiltrarFechas,
          getEntitiesByEcosistemaIdbyActivoDeTodoslosEcosistemasUSerLogueado
        ),
        (state, action) => {
          const { data, headers } = action.payload;

          return {
            ...state,
            loading: false,
            entities: data,
            totalItems: parseInt(headers['x-total-count'], 10),
          };
        }
      )
      .addMatcher(
        isFulfilled(createEntity, updateEntity, updateEntitysinRespuesta, partialUpdateEntity, updateEntityFechasFiltradas),
        (state, action) => {
          state.updating = false;
          state.loading = false;
          state.updateSuccess = true;
          state.entity = action.payload.data;
        }
      )
      .addMatcher(
        isPending(
          getEntities,
          getEntity,
          getEntitiesByEcosistema,
          getEntitiesByEcosistema1,
          getEntitiesByEcosistemabyIdUser,
          getEntitiesByEcosistematodasFiltrarFechas,
          getEntitiesByEcosistemaIdbyActivoDeTodoslosEcosistemasUSerLogueado
        ),
        state => {
          state.errorMessage = null;
          state.updateSuccess = false;
          state.loading = true;
        }
      )
      .addMatcher(
        isPending(createEntity, updateEntity, updateEntitysinRespuesta, updateEntityFechasFiltradas, partialUpdateEntity, deleteEntity),
        state => {
          state.errorMessage = null;
          state.updateSuccess = false;
          state.updating = true;
        }
      );
  },
});

export const { reset } = RetoSlice.actions;

// Reducer
export default RetoSlice.reducer;
