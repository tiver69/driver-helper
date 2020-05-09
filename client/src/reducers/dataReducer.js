import { GET_DATA, GET_SENSOR_DATA, POST_RESET_DATA } from "../actions/types";

const initialState = {
  dataNode: {},
  sensorDataNode: {}
};

export default function(state = initialState, action) {
  switch (action.type) {
    case GET_DATA:
      return {
        ...state,
        dataNode: action.payload
      };
    case GET_SENSOR_DATA:
      return {
        ...state,
        sensorDataNode: action.payload
      };
    case POST_RESET_DATA:
      return {
        ...state,
        dataNode: action.payload
      };

    default:
      return state;
  }
}
