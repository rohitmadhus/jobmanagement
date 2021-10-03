import "./App.css";
import HomeScreen from "./screens/home-screen";

function App() {
  return (
    <div className="grid-container">
      <header className="row">
        <div>
          <a className="brand" href="/">
            Job Management
          </a>
        </div>
      </header>
      <main>
        <HomeScreen></HomeScreen>
      </main>
      <footer className="row center">@All rights reserved</footer>
    </div>
  );
}

export default App;
