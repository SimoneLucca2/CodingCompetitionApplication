import React, { useState } from "react";
import {useNavigate} from "react-router-dom";
import './LoginSignup.css';
import user_icon from './Assets/person.png';
import email_icon from './Assets/email.png';
import password_icon from './Assets/password.png';
import immagine_icon from './Assets/guy.png';
import logo_icon from './Assets/Logo.png';
import Radiobutton from "../RadioButton/radiobutton";


const LoginSignup = () => {
    const [action, setAction] = useState("Sign Up");
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('');
    const [surname, setSurname] = useState('');
    const [nickname, setNickname] = useState('');
    const [type, setType] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    const navigate = useNavigate();
    const goTOuserprofile = () => {
        navigate('/userprofile'); // Naviga alla userprofile
    }

    const goTOerrorpage = () => {
        navigate('/errorpage'); // Naviga alla userprofile
    }


    const handleSubmit = async (event) => {
        event.preventDefault();

        // Basic validation
        if (!email || !password) {
            setErrorMessage('Email and password are required');
            console.log("Email and password are required")
            return;
        }

        try {
            const url = action === 'Login' ? 'http://192.168.232.18:8080/login' : 'http://192.168.232.18:8080/signup';
            const bodyData = action === 'Login' ? {email, password,type} : {email, password, name, surname, nickname, type};

            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(bodyData),
            });
            console.log("Added");



            if (!response.ok) {
                throw new Error(`${action} failed`);
                {goTOerrorpage()}
            }

            const data = await response.json();
            console.log(`${action} successful:`, data);

            {goTOuserprofile()};// Naviga alla userprofile

        } catch (error) {
            console.error(`${action} error:`, error);
            setErrorMessage(`${action} failed. Please try again.`);
            {goTOerrorpage()}
        }

    }

    return (
        <div className='container'>
            <form onSubmit={handleSubmit}>
                <div className="header">

                    <div className="text">"CKB: CODE KATA BATTLE"</div>
                    <div className="underline"></div>
                </div>

                <div className="Centro">
                    <div className="inputs">
                        <div className="submit-container">
                            <div className={action === "Login" ? "submit" : "submit gray"}
                                 onClick={() => setAction("Sign Up")}>Sign Up
                            </div>
                            <div className={action === "Sign Up" ? "submit" : "submit gray"}
                                 onClick={() => setAction("Login")}>Login
                            </div>
                        </div>
                        {action === "Login" ? <div></div> :
                            <div className="input">
                                <img src={user_icon} alt=""/>
                                <input type="text" placeholder="Name" value={name} onChange={(e) => setName(e.target.value)}/>
                            </div>
                        }
                        {action === "Login" ? <div></div> :
                            <div className="input">
                                <img src={user_icon} alt=""/>
                                <input type="text" placeholder="Surname" value={surname} onChange={(e) => setSurname(e.target.value)}/>
                            </div>
                        }
                        {action === "Login" ? <div></div> :
                            <div className="input">
                                <img src={user_icon} alt=""/>
                                <input type="text" placeholder="Nickname" value={nickname} onChange={(e) => setNickname(e.target.value)}/>
                            </div>
                        }
                        <div className="input">
                            <img src={email_icon} alt=""/>
                            <input
                                type="email"
                                placeholder="Email Id"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                            />
                        </div>
                        <div className="input">
                            <img src={password_icon} alt=""/>
                            <input
                                type="password"
                                placeholder="Password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                            />
                        </div>
                        <div className="input2">
                            <Radiobutton type="text" placeholder="Type" value={type} onChange={(e) => setType(e.target.value)}/>
                        </div>
                    </div>
                    <div className="immagine"><img src={immagine_icon} alt=""/></div>
                </div>
                <div className="centro2">
                    <div className="button">
                        <button type="submit" className="ButtonforSubmit">Submit</button>
                    </div>
                    <div className="immagine2"><img src={logo_icon} alt=""/>
                    </div>
                </div>
                {errorMessage && <div className="error-message">{errorMessage}</div>}
            </form>
        </div>
    );
};


export default LoginSignup;