import { GET_DATA, POST_RESET_DATA } from "../actions/types";

const initialState = {
  dataNode: {}
};

export default function(state = initialState, action) {
  switch (action.type) {
    case GET_DATA:
      return {
        ...state,
        dataNode: action.payload
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
