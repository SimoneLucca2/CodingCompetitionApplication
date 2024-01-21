import {ComponentPreview, Previews} from '@react-buddy/ide-toolbox'
import {PaletteTree} from './palette'
import TournamentCard from "../Components/Tournament/TournamentCard";
import StudentProfile from "../Components/Student/StudentProfile";
import Button from "../Components/Button/Button";
import App from "../App";
import Student from "../Components/Student/StudentCard";
import StudentCard from "../Components/Student/StudentCard";
import List from "../Components/List/List";
import {UseState} from "../Components/UseStateComponent/UseState";
import {OnChange} from "../Components/UseStateComponent/OnChange";
import {MyComponent} from "../Components/UseStateComponent/UpdateObjectInState/MyComponent";
import TournamentDisplay from "../Components/Tournament/TournamentDisplay";

const ComponentPreviews = () => {
    return (
        <Previews palette={<PaletteTree/>}>
            <ComponentPreview path="/PaletteTree">
                <PaletteTree/>
            </ComponentPreview>
            <ComponentPreview path="/TournamentCard">
                <TournamentCard/>
            </ComponentPreview>
            <ComponentPreview path="/StudentProfile">
                <StudentProfile/>
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
            <ComponentPreview path="/UseState">
                <UseState/>
            </ComponentPreview>
            <ComponentPreview path="/OnChange">
                <OnChange/>
            </ComponentPreview>
            <ComponentPreview path="/MyComponent">
                <MyComponent/>
            </ComponentPreview>
            <ComponentPreview path="/TournamentDisplay">
                <TournamentDisplay/>
            </ComponentPreview>
            <ComponentPreview path="/ComponentPreviews">
                <ComponentPreviews/>
            </ComponentPreview>
        </Previews>
    )
}

export default ComponentPreviews