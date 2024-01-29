
import {BrowserRouter, Route, Routes, } from "react-router-dom";
import './App.css';
import LoginSignup from "./Components/Login-SignUp/LoginSignup";
import TournamentCreationPage from "./Components/CreateTournament/createtournament";
import AddEducatorToTournament from "./Components/AddEducatorToaTournament/addeducatortoatournament";
import Jointournament from "./Components/JoinTournament/jointournament";
import QuitTournament from "./Components/SuccessPage/SuccessPage";
import CreateBattle from "./Components/CreateBattle/createbattle";
import ErrorPage from "./Components/ErrorPage/errorpage";
import JoinBattle from "./Components/JoinBattle/joinbattle";
import QuitBattle from "./Components/QuitBattle/quitbattle";
import {ItemListProvider} from "./Context/ItemListContext";
import TournamentsPageStudent  from "./Components/TournamentStudent/TournamentsPageStudent";
import TournamentsPageEducator  from "./Components/TournamentEducator/TournamentsPageEducator";
import BattlesPageStudent  from "./Components/BattleStudent/BattlesPageStudent";
import BattlesPageEducator  from "./Components/BattleEducator/BattlesPageEducator";
import TournamentsPageEducatormysection from "./Components/TournamentEducatormysection/TournamentsPageEducatormysection";
import TournamentsPageStudentmysection from "./Components/TournamentStudentmysection/TournamentsPageStudentmysection";
import BattlesPageEducatormysection from "./Components/BattleEducatormysection/BattlesPageEducatormysection";
import BattlesPageStudentmysection from "./Components/BattleStudentmysection/BattlesPageStudentmysection";
import CloseTournament from "./Components/CloseTournament/closetournament";
import StudentProfile from "./Components/StudentProfile/StudentProfile";
import Educatorprofile from "./Components/EducatorProfile/educatorprofile";
import SuccessPage  from "./Components/SuccessPage/SuccessPage";

function App() {
  return (
      <ItemListProvider>
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<LoginSignup/>}/>
            <Route path="/createtournament" element={<TournamentCreationPage/>}/>
            <Route path="/addeducatortoatournament/:tournamentId" element={<AddEducatorToTournament/>}/>
            <Route path="/jointournament" element={<Jointournament/>}/>
            <Route path="/quittournament" element={<QuitTournament/>}/>
            <Route path="/createbattle/:tournamentId" element={<CreateBattle/>}/>
            <Route path="/errorpage" element={<ErrorPage/>}/>
            <Route path="/joinbattle" element={<JoinBattle/>}/>
            <Route path="/quitbattle" element={<QuitBattle/>}/>
            <Route path="/tournamentspagestudent" element={<TournamentsPageStudent/>}/>
            <Route path="/tournamentspageeducator" element={<TournamentsPageEducator/>}/>
            <Route path="/battlespagestudent/:tournamentId" element={<BattlesPageStudent/>}/>
            <Route path="/battlespageeducator/:tournamentId" element={<BattlesPageEducator/>}/>
            <Route path="/studentprofile" element={<StudentProfile/>}/>
            <Route path="/educatorprofile" element={<Educatorprofile/>}/>
            <Route path="/mysectiontournamentspageeducator" element={<TournamentsPageEducatormysection/>}/>
            <Route path="/mysectiontournamentspagestudent" element={<TournamentsPageStudentmysection/>}/>
            <Route path="/mysectiontbattlespagestudent/:tournamentId" element={<BattlesPageStudentmysection/>}/>
            <Route path="/mysectionbattlespageeducator/:tournamentId" element={<BattlesPageEducatormysection/>}/>
            <Route path="/closetournament" element={<CloseTournament/>}/>
            <Route path="/successpage" element={<SuccessPage/>}/>
          </Routes>
        </BrowserRouter>
      </ItemListProvider>

  );
}


export default App;
