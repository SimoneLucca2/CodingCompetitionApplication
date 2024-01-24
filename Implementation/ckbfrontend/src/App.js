
import {BrowserRouter, Route, Routes, } from "react-router-dom";
import './App.css';
import LoginSignup from "./Components/Login-SignUp/LoginSignup";
import TournamentCreationPage from "./Components/CreateTournament/createtournament";
import AddEducatorToTournament from "./Components/AddEducatorToaTournament/addeducatortoatournament";
import Jointournament from "./Components/JoinTournament/jointournament";
import QuitTournament from "./Components/QuitTournament/quittournament";
import CreateBattle from "./Components/CreateBattle/createbattle";
import ErrorPage from "./Components/ErrorPage/errorpage";
import JoinBattle from "./Components/JoinBattle/joinbattle";
import QuitBattle from "./Components/QuitBattle/quitbattle";
import {ItemListProvider} from "./Context/ItemListContext";
import TournamentsPage  from "./Components/Tournament2/TournamentsPage";
import BattlesPage  from "./Components/Battle/BattlesPage";
function App() {
  return (
      <ItemListProvider>
        <BrowserRouter>
          <Routes>

            <Route path="/" element={<LoginSignup/>}/>
            <Route path="/createtournament" element={<TournamentCreationPage/>}/>
            <Route path="/addeducatortoatournament" element={<AddEducatorToTournament/>}/>
            <Route path="/jointournament/:tournamentId" element={<Jointournament/>}/>
            <Route path="/quittournament" element={<QuitTournament/>}/>
            <Route path="/createbattle" element={<CreateBattle/>}/>
            <Route path="/errorpage" element={<ErrorPage/>}/>
            <Route path="/joinbattle" element={<JoinBattle/>}/>
            <Route path="/quitbattle" element={<QuitBattle/>}/>
            <Route path="/tournaments" element={<TournamentsPage/>}/>
            <Route path="/tournaments/:tournamentId/battles" element={<BattlesPage/>}/>

          </Routes>
        </BrowserRouter>
      </ItemListProvider>

  );
}


export default App;
