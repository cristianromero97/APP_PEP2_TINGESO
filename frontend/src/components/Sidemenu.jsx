import * as React from "react";
import Box from "@mui/material/Box";
import Drawer from "@mui/material/Drawer";
import List from "@mui/material/List";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import HomeIcon from "@mui/icons-material/Home";
import PeopleAltIcon from "@mui/icons-material/PeopleAlt";
import Divider from "@mui/material/Divider";
import { useNavigate } from "react-router-dom";
import DirectionsCarIcon from "@mui/icons-material/DirectionsCar";
import MonetizationOnIcon from '@mui/icons-material/MonetizationOn';
import LocalOfferIcon from '@mui/icons-material/LocalOffer';
import EventAvailableIcon from '@mui/icons-material/EventAvailable';
import InsertChartIcon from "@mui/icons-material/InsertChart";
import CalendarMonthIcon from '@mui/icons-material/CalendarMonth';
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';
export default function Sidemenu({ open, toggleDrawer }) {
  const navigate = useNavigate();

  const listOptions = () => (
    <Box role="presentation" onClick={toggleDrawer(false)} sx={{ width: 250 }}>
      <List>
        <ListItemButton onClick={() => navigate("/home")}>
          <ListItemIcon>
            <HomeIcon />
          </ListItemIcon>
          <ListItemText primary="Inicio" />
        </ListItemButton>

        <Divider />

        <ListItemButton onClick={() => navigate("/clients")}>
          <ListItemIcon>
          <PeopleAltIcon />
          </ListItemIcon>
          <ListItemText primary="Clientes" />
        </ListItemButton>

        <Divider />

        <ListItemButton onClick={() => navigate("/karts")}>
          <ListItemIcon>
          <DirectionsCarIcon />
          </ListItemIcon>
          <ListItemText primary="Karts" />
        </ListItemButton>

        <Divider />

        <ListItemButton onClick={() => navigate("/rates")}>
          <ListItemIcon>
          <MonetizationOnIcon />
          </ListItemIcon>
          <ListItemText primary="Tarifas" />
        </ListItemButton>

        <Divider />

         <ListItemButton onClick={() => navigate("/discount-day")}>
          <ListItemIcon>
          <LocalOfferIcon />
          </ListItemIcon>
          <ListItemText primary="Descuentos Days" />
        </ListItemButton>

        <Divider />

         <ListItemButton onClick={() => navigate("/discounts")}>
          <ListItemIcon>
          <LocalOfferIcon />
          </ListItemIcon>
          <ListItemText primary="Descuentos Grupal" />
        </ListItemButton>

        <Divider />

        <ListItemButton onClick={() => navigate("/discount-visit")}>
          <ListItemIcon>
          <LocalOfferIcon />
          </ListItemIcon>
          <ListItemText primary="Descuentos Visitas" />
        </ListItemButton>

        <Divider />

        <ListItemButton onClick={() => navigate("/reservations")}>
          <ListItemIcon>
          <EventAvailableIcon />
          </ListItemIcon>
          <ListItemText primary="Reservaciones" />
        </ListItemButton>

        <Divider />

        <ListItemButton onClick={() => navigate("/payments")}>
          <ListItemIcon>
          <AttachMoneyIcon />
          </ListItemIcon>
          <ListItemText primary="Pagos" />
        </ListItemButton>

        <Divider />
  
        <ListItemButton onClick={() => navigate("/reports")}>
          <ListItemIcon>
          <InsertChartIcon />
          </ListItemIcon>
          <ListItemText primary="Reporte" />
        </ListItemButton>

        <Divider />
  
         <ListItemButton onClick={() => navigate("/weekly-rack")}>
          <ListItemIcon>
          <CalendarMonthIcon  />
          </ListItemIcon>
          <ListItemText primary="Racks" />
          </ListItemButton>

      </List>
    </Box>
  );

  return (
    <Drawer anchor="left" open={open} onClose={toggleDrawer(false)}>
      {listOptions()}
    </Drawer>
  );
}