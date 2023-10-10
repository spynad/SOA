import './App.css';
import {SnackbarProvider} from "notistack";
import {useEffect} from "react";
import {BrowserRouter, Navigate, Route, Routes} from "react-router-dom";
import TicketsCatalogPage from "./pages/catalog-page";
import {BookingPage} from "./pages/booking-page";
import {ThemeSwitcherProvider} from "react-css-theme-switcher";
import {xml_axios} from "./utils/api";
import convert from "xml-js";

function App() {
  const currThemes = {
    dark: `${process.env.PUBLIC_URL}/antd.dark-theme.css`,
    light: `${process.env.PUBLIC_URL}/antd.light-theme.css`,
  };

  return (
      <ThemeSwitcherProvider themeMap={currThemes} defaultTheme="light">
        <SnackbarProvider maxSnack={3}>
          <BrowserRouter>
              <Routes>
                <Route path={"/catalog"} element={<TicketsCatalogPage/>}/>
                <Route path={"/booking"} element={<BookingPage/>}/>
                <Route path={"*"} element={<Navigate to={"/catalog"} replace/>}/>
              </Routes>
          </BrowserRouter>
        </SnackbarProvider>
      </ThemeSwitcherProvider>
  );
}

export default App;