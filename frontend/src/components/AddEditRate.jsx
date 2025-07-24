import React, { useState, useEffect } from "react";
import { useParams, useNavigate, Link } from "react-router-dom";
import rateService from "../services/rate.service"; 
import {
  Box,
  TextField,
  Button,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
} from "@mui/material";
import SaveIcon from "@mui/icons-material/Save";

const AddEditRate = () => {
  const [laps, setLaps] = useState("");
  const [maximumTimeLaps, setMaximumTimeLaps] = useState("");
  const [price, setPrice] = useState("");
  const [maximumTimeReservation, setMaximumTimeReservation] = useState("");
  const { id } = useParams();
  const navigate = useNavigate();
  const [title, setTitle] = useState("Nueva Tarifa");

  useEffect(() => {
    if (id) {
      setTitle("Editar Tarifa");
      rateService
        .get(id)
        .then((response) => {
          const data = response.data;
          setLaps(data.laps);
          setMaximumTimeLaps(data.maximumTimeLaps);
          setPrice(data.price);
          setMaximumTimeReservation(data.maximumTimeReservation);
        })
        .catch((error) => {
          console.error("Error al obtener la tarifa:", error);
        });
    }
  }, [id]);

  const saveRate = (e) => {
    e.preventDefault();
    const rate = {
      laps,
      maximumTimeLaps,
      price,
      maximumTimeReservation,
    };

    if (id) {
      rateService
        .update(id, rate)
        .then(() => {
          alert("Tarifa actualizada correctamente");
          navigate("/rates");
        })
        .catch((error) => {
          console.error("Error al actualizar la tarifa:", error);
        });
    } else {
      rateService
        .create(rate)
        .then(() => {
          alert("Tarifa creada correctamente");
          navigate("/rates");
        })
        .catch((error) => {
          console.error("Error al crear la tarifa:", error);
        });
    }
  };

  return (
    <Box
      display="flex"
      flexDirection="column"
      alignItems="center"
      justifyContent="center"
      component="form"
    >
      <h3>{title}</h3>
      <hr />
      <form>
        <FormControl fullWidth margin="normal">
          <TextField
            id="laps"
            label="Vueltas"
            type="number"
            value={laps}
            variant="standard"
            onChange={(e) => setLaps(e.target.value)}
          />
        </FormControl>

        <FormControl fullWidth margin="normal">
          <TextField
            id="maximumTimeLaps"
            label="Tiempo máximo en vueltas"
            type="number"
            value={maximumTimeLaps}
            variant="standard"
            onChange={(e) => setMaximumTimeLaps(e.target.value)}
          />
        </FormControl>

        <FormControl fullWidth margin="normal">
          <TextField
            id="price"
            label="Precio"
            type="number"
            value={price}
            variant="standard"
            onChange={(e) => setPrice(e.target.value)}
          />
        </FormControl>

        <FormControl fullWidth margin="normal">
          <TextField
            id="maximumTimeReservation"
            label="Tiempo máximo de reserva"
            type="number"
            value={maximumTimeReservation}
            variant="standard"
            onChange={(e) => setMaximumTimeReservation(e.target.value)}
          />
        </FormControl>

        <FormControl margin="normal">
          <Button
            variant="contained"
            color="primary"
            onClick={saveRate}
            startIcon={<SaveIcon />}
          >
            Grabar
          </Button>
        </FormControl>
      </form>
      <hr />
      <Link to="/rates">Volver a la Lista</Link>
    </Box>
  );
};

export default AddEditRate;