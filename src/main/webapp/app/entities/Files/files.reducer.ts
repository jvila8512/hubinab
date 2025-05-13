import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending, isRejected } from '@reduxjs/toolkit';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';

import { IFile, defaultValue } from 'app/shared/model/file.model';

const initialState: EntityState<IFile> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

const apiUrl = 'api/file';

// Actions

export const getEntity = createAsyncThunk(
  'file/entity_file',
  async (name: string) => {
    const requestUrl = `${apiUrl}/${name}`;
    return axios.get<IFile>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

export const getArchivo = async (name: string) => {
  const requestUrl = `${apiUrl}/${name}`;
  return axios.get<IFile>(requestUrl);
};

export const deletefile = async (nombre: string) => {
  const requestUrl = `${apiUrl}/delete/${nombre}`;

  return axios.delete<IFile>(requestUrl);
};

export const getEntities = createAsyncThunk(
  'file/entity_fileS',
  async (name: string) => {
    const requestUrl = `${apiUrl}/all`;
    return axios.get<IFile[]>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

export const createEntity = createAsyncThunk(
  'file/create_file',
  async (entity: FormData, thunkAPI) => {
    const requestUrl = `${apiUrl}/upload`;
    const result = await axios.post(requestUrl, entity);
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const deleteEntity = createAsyncThunk(
  'file/delete_file',
  async (nombre: string, thunkAPI) => {
    const requestUrl = `${apiUrl}/delete/${nombre}`;
    const result = await axios.delete<IFile>(requestUrl);

    return result;
  },
  { serializeError: serializeAxiosError }
);

// slice

export const FilesSlice = createEntitySlice({
  name: 'file',
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
      .addMatcher(isFulfilled(getEntities), (state, action) => {
        const { data } = action.payload;

        return {
          ...state,
          loading: false,
          entities: data,
        };
      })
      .addMatcher(isFulfilled(createEntity), (state, action) => {
        state.updating = false;
        state.loading = false;
        state.updateSuccess = true;
        state.entity = action.payload.data;
      })
      .addMatcher(isPending(getEntities, getEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isPending(createEntity, deleteEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.updating = true;
      });
  },
});

export const { reset } = FilesSlice.actions;

// Reducer
export default FilesSlice.reducer;
