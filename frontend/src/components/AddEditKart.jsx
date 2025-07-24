import React, { useState, useEffect } from "react";
import { useParams, useNavigate, Link } from "react-router-dom";
import kartService from "../services/kart.service"; 
import {
  Box,
  TextField,
  Button,
  FormControl,
  Select,
  MenuItem,
  InputLabel,
} from "@mui/material";
import SaveIcon from "@mui/icons-material/Save";

const AddEditKart = () => {
  const [model, setModel] = useState("");
  const [available, setAvailable] = useState("");
  const { id } = useParams();
  const navigate = useNavigate();
  const [title, setTitle] = useState("Nuevo Kart");

  useEffect(() => {
    if (id) {
      setTitle("Editar Kart");
      kartService
        .get(id)
        .then((response) => {
          const data = response.data;
          setModel(data.model);
          setAvailable(data.available);
        })
        .catch((error) => {
          console.error("Error al obtener el kart:", error);
        });
    }
  }, [id]);

  const saveKart = (e) => {
    e.preventDefault();
    const kart = { model, available };

    if (id) {
      kartService
        .update(id, kart)
        .then(() => {
          alert("Kart actualizado correctamente");
          navigate("/karts");
        })
        .catch((error) => {
          console.error("Error al actualizar el kart:", error);
        });
    } else {
      kartService
        .create(kart)
        .then(() => {
          alert("Kart creado correctamente");
          navigate("/karts");
        })
        .catch((error) => {
          console.error("Error al crear el kart:", error);
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
            id="model"
            label="Modelo"
            value={model}
            variant="standard"
            onChange={(e) => setModel(e.target.value)}
          />
        </FormControl>

        <FormControl fullWidth margin="normal">
          <InputLabel id="available-label">Disponibilidad</InputLabel>
          <Select
            labelId="available-label"
            id="available"
            value={available}
            onChange={(e) => setAvailable(e.target.value)}
            variant="standard"
          >
            <MenuItem value="Disponible">Disponible</MenuItem>
            <MenuItem value="No Disponible">No Disponible</MenuItem>
          </Select>
        </FormControl>

        <FormControl margin="normal">
          <Button
            variant="contained"
            color="primary"
            onClick={saveKart}
            startIcon={<SaveIcon />}
          >
            Grabar
          </Button>
        </FormControl>
      </form>
      <hr />
      <Link to="/karts">Volver a la Lista</Link>
    </Box>
  );
};

export default AddEditKart;
