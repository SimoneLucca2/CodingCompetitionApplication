import {BrowserRouter, Route, Routes, } from "react-router-dom";
import './App.css';
import LoginSignup from "./Components/Login-SignUp/LoginSignup";
import UserProfile from "./Components/UserProfile/userprofile";
import Createtournament from "./Components/CreateTournament/createtournament";
import TournamentCreationPage from "./Components/CreateTournament/createtournament";

function App() {
  return (
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<LoginSignup />} />
          <Route path="/userprofile" element={<UserProfile />} />
          <Route path="/createtournament" element={<TournamentCreationPage />} />
        </Routes>
      </BrowserRouter>
  );
}


export default App;
