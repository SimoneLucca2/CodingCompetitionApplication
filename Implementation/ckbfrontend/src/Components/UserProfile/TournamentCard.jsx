import tournamentPic from "./Assets/tournamentPic.png";
import './styles/TournamentCard.css';


function TournamentCard() {
    return (
        <div className="tournament-card">
            <img className="tournament-card-image" src={tournamentPic} alt="tournament picture"></img>
            <h2 className="tournament-card-title">TOURNAMENT NAME</h2>
            <p className="tournament-card-text">TOURNAMENT DESCRIPTION</p>
        </div>
);
}

export default TournamentCard;

