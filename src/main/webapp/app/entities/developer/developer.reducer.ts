import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDeveloper, defaultValue } from 'app/shared/model/developer.model';

export const ACTION_TYPES = {
  FETCH_DEVELOPER_LIST: 'developer/FETCH_DEVELOPER_LIST',
  FETCH_DEVELOPER: 'developer/FETCH_DEVELOPER',
  CREATE_DEVELOPER: 'developer/CREATE_DEVELOPER',
  UPDATE_DEVELOPER: 'developer/UPDATE_DEVELOPER',
  DELETE_DEVELOPER: 'developer/DELETE_DEVELOPER',
  RESET: 'developer/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDeveloper>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type DeveloperState = Readonly<typeof initialState>;

// Reducer

export default (state: DeveloperState = initialState, action): DeveloperState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DEVELOPER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DEVELOPER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_DEVELOPER):
    case REQUEST(ACTION_TYPES.UPDATE_DEVELOPER):
    case REQUEST(ACTION_TYPES.DELETE_DEVELOPER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_DEVELOPER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DEVELOPER):
    case FAILURE(ACTION_TYPES.CREATE_DEVELOPER):
    case FAILURE(ACTION_TYPES.UPDATE_DEVELOPER):
    case FAILURE(ACTION_TYPES.DELETE_DEVELOPER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_DEVELOPER_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_DEVELOPER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_DEVELOPER):
    case SUCCESS(ACTION_TYPES.UPDATE_DEVELOPER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_DEVELOPER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/developers';

// Actions

export const getEntities: ICrudGetAllAction<IDeveloper> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_DEVELOPER_LIST,
    payload: axios.get<IDeveloper>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IDeveloper> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DEVELOPER,
    payload: axios.get<IDeveloper>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IDeveloper> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DEVELOPER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDeveloper> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DEVELOPER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDeveloper> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DEVELOPER,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
