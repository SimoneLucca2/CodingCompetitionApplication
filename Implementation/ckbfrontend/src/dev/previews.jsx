import {ComponentPreview, Previews} from '@react-buddy/ide-toolbox'
import {PaletteTree} from './palette'
import TournamentCard from "../Components/UserProfile/TournamentCard";
import UserProfile from "../Components/UserProfile/userprofile";
import Button from "../Components/Button/Button";
import App from "../App";
import Student from "../Components/Student/StudentCard";
import StudentCard from "../Components/Student/StudentCard";
import List from "../Components/List/List";

const ComponentPreviews = () => {
    return (
        <Previews palette={<PaletteTree/>}>
            <ComponentPreview path="/PaletteTree">
                <PaletteTree/>
            </ComponentPreview>
            <ComponentPreview path="/TournamentCard">
                <TournamentCard/>
            </ComponentPreview>
            <ComponentPreview path="/UserProfile">
                <UserProfile/>
            </ComponentPreview>
            <ComponentPreview path="/Button">
                <Button/>
            </ComponentPreview>
            <ComponentPreview path="/App">
                <App/>
            </ComponentPreview>
            <ComponentPreview path="/Student">
                <Student/>
            </ComponentPreview>
            <ComponentPreview path="/StudentCard">
                <StudentCard/>
            </ComponentPreview>
            <ComponentPreview path="/List">
                <List/>
            </ComponentPreview>
        </Previews>
    )
}

export default ComponentPreviews