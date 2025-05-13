import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending, isRejected } from '@reduxjs/toolkit';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { INotificacion, defaultValue } from 'app/shared/model/notificacion.model';

const initialState: EntityState<INotificacion> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

const apiUrl = 'api/notificacion';

// Actions

export const getEntities = createAsyncThunk('notificacion/fetch_entity_list', async ({ page, size, sort }: IQueryParams) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}&` : '?'}cacheBuster=${new Date().getTime()}`;
  return axios.get<INotificacion[]>(requestUrl);
});

export const getTodasEntities = createAsyncThunk('notificacion/fetch_entity_list_todas', async () => {
  const requestUrl = `${apiUrl}`;
  return axios.get<INotificacion[]>(requestUrl);
});

export const getTodasEntitiesbyUser = createAsyncThunk('notificacion/fetch_entity_list_todasby_User', async (iduser: string | number) => {
  const requestUrl = `${apiUrl}/user/${iduser}`;
  return axios.get<INotificacion[]>(requestUrl);
});
export const getTodasEntitiesbyUserVista = createAsyncThunk(
  'notificacion/fetch_entity_list_todasby_User',
  async (iduser: string | number) => {
    const requestUrl = `${apiUrl}/user-visto/${iduser}`;
    return axios.get<INotificacion[]>(requestUrl);
  }
);
export const getTodasEntitiesbyUserNoVista = createAsyncThunk(
  'notificacion/fetch_entity_list_todasby_User',
  async (iduser: string | number) => {
    const requestUrl = `${apiUrl}/user-no-visto/${iduser}`;
    return axios.get<INotificacion[]>(requestUrl);
  }
);

export const getTodasEntitiesbyUserCreada = createAsyncThunk(
  'notificacion/fetch_entity_list_todasby_User',
  async (idusercreada: string | number) => {
    const requestUrl = `${apiUrl}/usercreada/${idusercreada}`;
    return axios.get<INotificacion[]>(requestUrl);
  }
);

export const crearNotificacionRetosUserEcosistema = async (idecosistema, notificacion) => {
  const requestUrl = `${apiUrl}/SinMensajeParaTodos/${idecosistema}`;
  return axios.post(requestUrl, cleanEntity(notificacion));
};

export const createEntitySinMensaje1 = async notificacion => {
  const requestUrl = `${apiUrl}/SinMensaje`;
  const result = await axios.post<INotificacion>(requestUrl, cleanEntity(notificacion));

  return result;
};

export const getEntity = createAsyncThunk(
  'notificacion/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<INotificacion>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

export const createEntity = createAsyncThunk(
  'notificacion/create_entity',
  async (entity: INotificacion, thunkAPI) => {
    const requestUrl = `${apiUrl}`;
    const result = await axios.post<INotificacion>(requestUrl, cleanEntity(entity));

    return result;
  },
  { serializeError: serializeAxiosError }
);
export const createEntitySinMensaje = createAsyncThunk(
  'notificacion/create_entity/SinMensaje',
  async (entity: INotificacion, thunkAPI) => {
    const requestUrl = `${apiUrl}/SinMensaje`;
    const result = await axios.post<INotificacion>(requestUrl, cleanEntity(entity));

    return result;
  },
  { serializeError: serializeAxiosError }
);

export const updateEntity = createAsyncThunk(
  'notificacion/update_entity',
  async (entity: INotificacion, thunkAPI) => {
    const result = await axios.put<INotificacion>(`${apiUrl}/${entity.id}`, cleanEntity(entity));

    return result;
  },
  { serializeError: serializeAxiosError }
);
export const vistaUpdateEntity = createAsyncThunk(
  'notificacion/update_entity/VistaUpdate',
  async (entity: INotificacion, thunkAPI) => {
    const result = await axios.put<INotificacion>(`${apiUrl}/vista/${entity.id}`, cleanEntity(entity));

    return result;
  },
  { serializeError: serializeAxiosError }
);

export const partialUpdateEntity = createAsyncThunk(
  'notificacion/partial_update_entity',
  async (entity: INotificacion, thunkAPI) => {
    const result = await axios.patch<INotificacion>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const deleteEntity = createAsyncThunk(
  'notificacion/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    const result = await axios.delete<INotificacion>(requestUrl);
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const deleteEntitySinMensaje = createAsyncThunk(
  'notificacion/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/deleteSinMensaje/${id}`;
    const result = await axios.delete<INotificacion>(requestUrl);

    return result;
  },
  { serializeError: serializeAxiosError }
);

// slice

export const NotificacionSlice = createEntitySlice({
  name: 'notificacion',
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
          getTodasEntities,
          getTodasEntitiesbyUser,
          getTodasEntitiesbyUserVista,
          getTodasEntitiesbyUserNoVista,
          getTodasEntitiesbyUserCreada
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
      .addMatcher(isFulfilled(createEntity, updateEntity, vistaUpdateEntity, partialUpdateEntity), (state, action) => {
        state.updating = false;
        state.loading = false;
        state.updateSuccess = true;
        state.entity = action.payload.data;
      })
      .addMatcher(
        isPending(
          getEntities,
          getEntity,
          getTodasEntities,
          getTodasEntitiesbyUser,
          getTodasEntitiesbyUserVista,
          getTodasEntitiesbyUserNoVista,
          getTodasEntitiesbyUserCreada
        ),
        state => {
          state.errorMessage = null;
          state.updateSuccess = false;
          state.loading = true;
        }
      )
      .addMatcher(isPending(createEntity, updateEntity, vistaUpdateEntity, partialUpdateEntity, deleteEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.updating = true;
      });
  },
});

export const { reset } = NotificacionSlice.actions;

// Reducer
export default NotificacionSlice.reducer;
