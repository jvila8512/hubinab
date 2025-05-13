import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending, isRejected } from '@reduxjs/toolkit';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { IProyectos, defaultValue } from 'app/shared/model/proyectos.model';

const initialState: EntityState<IProyectos> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

const apiUrl = 'api/proyectos';

// Actions

export const getEntities = createAsyncThunk('proyectos/fetch_entity_list', async ({ page, size, sort }: IQueryParams) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}&` : '?'}cacheBuster=${new Date().getTime()}`;
  return axios.get<IProyectos[]>(requestUrl);
});

export const contarProyectosbyEcosistemas = async id => {
  const requestUrl = `${apiUrl}/contarEcosistemas/${id}`;
  return axios.get(requestUrl);
};

export const getEntitiesEcosistema = createAsyncThunk('proyectos/fetch_entity_list_ecosistemas', async (id: string | number) => {
  const requestUrl = `${apiUrl}/byecosistema/${id}`;
  return axios.get<IProyectos[]>(requestUrl);
});
export const getEntitiesTodos = createAsyncThunk('proyectos/fetch_entity_list_ecosistemas_Todossssssssss', async () => {
  const requestUrl = `${apiUrl}/byecosistemaTodos`;
  return axios.get<IProyectos[]>(requestUrl);
});

export const getEntity = createAsyncThunk(
  'proyectos/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<IProyectos>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

export const createEntity = createAsyncThunk(
  'proyectos/create_entity',
  async (entity: IProyectos, thunkAPI) => {
    const result = await axios.post<IProyectos>(apiUrl, cleanEntity(entity));

    return result;
  },
  { serializeError: serializeAxiosError }
);

export const updateEntity = createAsyncThunk(
  'proyectos/update_entity',
  async (entity: IProyectos, thunkAPI) => {
    const result = await axios.put<IProyectos>(`${apiUrl}/${entity.id}`, cleanEntity(entity));

    return result;
  },
  { serializeError: serializeAxiosError }
);

export const partialUpdateEntity = createAsyncThunk(
  'proyectos/partial_update_entity',
  async (entity: IProyectos, thunkAPI) => {
    const result = await axios.patch<IProyectos>(`${apiUrl}/${entity.id}`, cleanEntity(entity));

    return result;
  },
  { serializeError: serializeAxiosError }
);

export const deleteEntity = createAsyncThunk(
  'proyectos/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    const result = await axios.delete<IProyectos>(requestUrl);

    return result;
  },
  { serializeError: serializeAxiosError }
);

export const getEntitiesByEcosistemaIdbyActivoDeTodoslosEcosistemasUSerLogueado = createAsyncThunk(
  `${apiUrl}/proyectosTodosByEcosistemasId`,
  async (idecosistemas: []) => {
    const requestUrl = `${apiUrl}/proyectosTodosByEcosistemasId/${idecosistemas}`;
    return axios.get<IProyectos[]>(requestUrl);
  }
);

// slice

export const ProyectosSlice = createEntitySlice({
  name: 'proyectos',
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
          getEntitiesEcosistema,
          getEntitiesByEcosistemaIdbyActivoDeTodoslosEcosistemasUSerLogueado,
          getEntitiesTodos
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
      .addMatcher(isFulfilled(createEntity, updateEntity, partialUpdateEntity), (state, action) => {
        state.updating = false;
        state.loading = false;
        state.updateSuccess = true;
        state.entity = action.payload.data;
      })
      .addMatcher(
        isPending(
          getEntities,
          getEntity,
          getEntitiesEcosistema,
          getEntitiesByEcosistemaIdbyActivoDeTodoslosEcosistemasUSerLogueado,
          getEntitiesTodos
        ),
        state => {
          state.errorMessage = null;
          state.updateSuccess = false;
          state.loading = true;
        }
      )
      .addMatcher(isPending(createEntity, updateEntity, partialUpdateEntity, deleteEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.updating = true;
      });
  },
});

export const { reset } = ProyectosSlice.actions;

// Reducer
export default ProyectosSlice.reducer;
