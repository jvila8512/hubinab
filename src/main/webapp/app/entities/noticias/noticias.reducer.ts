import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending, isRejected } from '@reduxjs/toolkit';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { INoticias, defaultValue } from 'app/shared/model/noticias.model';

const initialState: EntityState<INoticias> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

const apiUrl = 'api/noticias';

// Actions

export const getEntities = createAsyncThunk('noticias/fetch_entity_list', async ({ page, size, sort }: IQueryParams) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}&` : '?'}cacheBuster=${new Date().getTime()}`;
  return axios.get<INoticias[]>(requestUrl);
});

export const getEntitiesByEcosistemaIdbyPublicoDeTodoslosEcosistemasUSerLogueado = createAsyncThunk(
  `${apiUrl}/NoticiasTodosByEcosistemas`,
  async (idecosistemas: []) => {
    const requestUrl = `${apiUrl}/noticiasTodosByEcosistemasId/${idecosistemas}`;
    return axios.get<INoticias[]>(requestUrl);
  }
);

export const getALL = createAsyncThunk('noticias/fetch_entity_ALL', async () => {
  const requestUrl = `${apiUrl}`;
  return axios.get<INoticias[]>(requestUrl);
});
export const contarNoticiasbyEcosistemas = async id => {
  const requestUrl = `${apiUrl}/contarEcosistemas/${id}`;
  return axios.get(requestUrl);
};

export const getNoticias = createAsyncThunk('noticias/fetch_entity_list-todas', async () => {
  const requestUrl = `${apiUrl}/publicar`;
  return axios.get<INoticias[]>(requestUrl);
});

export const getNoticiasByEcosistemaId = createAsyncThunk('noticias/fetch_entity_list-todas', async (id: string | number) => {
  const requestUrl = `${apiUrl}/byecosistema/${id}`;
  return axios.get<INoticias[]>(requestUrl);
});

export const getNoticiasByPublicabyEcosistemaIdbyUserId = createAsyncThunk(
  'noticias/fetch_entity_list-by-publica-ecosistema-user',
  async ({ id, iduser }: IQueryParams) => {
    const requestUrl = `${apiUrl}/noticiasbyecosistemabyuser/${id}/${iduser}`;
    return axios.get<INoticias[]>(requestUrl);
  }
);
export const getNoticiasByPublicabyEcosistemaIdbyUserIdSolo = createAsyncThunk(
  'noticias/fetch_entity_list-by-publica-ecosistema-user-Solo',
  async ({ id, iduser }: IQueryParams) => {
    const requestUrl = `${apiUrl}/noticiasbyecosistemabyusersolo/${id}/${iduser}`;
    return axios.get<INoticias[]>(requestUrl);
  }
);
export const getNoticiasByPublicabyEcosistemaIdbyUserId_Paginado = createAsyncThunk(
  'noticias/fetch_entity_list-by-publica-ecosistema-user_paginado',
  async ({ id, iduser, page, size, sort }: IQueryParams) => {
    const requestUrl = `${apiUrl}/noticiasbyecosistemabyuserpaginado/${id}${`?page=${page}&size=${size}&sort=${sort}`}`;
    return axios.get<INoticias[]>(requestUrl);
  }
);

export const getEntity = createAsyncThunk(
  'noticias/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<INoticias>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

export const createEntity = createAsyncThunk(
  'noticias/create_entity',
  async (entity: INoticias, thunkAPI) => {
    const result = await axios.post<INoticias>(apiUrl, cleanEntity(entity));

    return result;
  },
  { serializeError: serializeAxiosError }
);

export const updateEntity = createAsyncThunk(
  'noticias/update_entity',
  async (entity: INoticias, thunkAPI) => {
    const result = await axios.put<INoticias>(`${apiUrl}/${entity.id}`, cleanEntity(entity));

    return result;
  },
  { serializeError: serializeAxiosError }
);

export const partialUpdateEntity = createAsyncThunk(
  'noticias/partial_update_entity',
  async (entity: INoticias, thunkAPI) => {
    const result = await axios.patch<INoticias>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const deleteEntity = createAsyncThunk(
  'noticias/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    const result = await axios.delete<INoticias>(requestUrl);

    return result;
  },
  { serializeError: serializeAxiosError }
);

// slice

export const NoticiasSlice = createEntitySlice({
  name: 'noticias',
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
          getNoticias,
          getNoticiasByEcosistemaId,
          getNoticiasByPublicabyEcosistemaIdbyUserId,
          getNoticiasByPublicabyEcosistemaIdbyUserId_Paginado,
          getNoticiasByPublicabyEcosistemaIdbyUserIdSolo,

          getEntitiesByEcosistemaIdbyPublicoDeTodoslosEcosistemasUSerLogueado
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
          getNoticias,
          getNoticiasByEcosistemaId,
          getNoticiasByPublicabyEcosistemaIdbyUserId,
          getNoticiasByPublicabyEcosistemaIdbyUserId_Paginado,
          getNoticiasByPublicabyEcosistemaIdbyUserIdSolo,
          getEntitiesByEcosistemaIdbyPublicoDeTodoslosEcosistemasUSerLogueado
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

export const { reset } = NoticiasSlice.actions;

// Reducer
export default NoticiasSlice.reducer;
