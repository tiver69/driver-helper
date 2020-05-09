import { combineReducers } from "redux";
import dataReducer from "./dataReducer";
import settingsReducer from "./settingsReducer";
import errorReducer from "./errorReducer";

export default combineReducers({
  dataNode: dataReducer,
  settings: settingsReducer,
  error: errorReducer
});
