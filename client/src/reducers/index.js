import { combineReducers } from "redux";
import dataReducer from "./dataReducer";
import carReducer from "./carReducer";

export default combineReducers({
  dataNode: dataReducer,
  car: carReducer
});
