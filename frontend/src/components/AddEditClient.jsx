import React, { useState, useEffect } from "react";
import { useParams, useNavigate, Link } from "react-router-dom";
import clientService from "../services/client.service";
import {
  Box,
  TextField,
  Button,
  FormControl,
} from "@mui/material";
import SaveIcon from "@mui/icons-material/Save";

const AddEditClient = () => {
  const [rut, setRut] = useState("");
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [birthDate, setBirthDate] = useState("");
  const [monthlyVisits, setMonthlyVisits] = useState("");
  const { id } = useParams();
  const navigate = useNavigate();
  const [title, setTitle] = useState("Nuevo Cliente");

  useEffect(() => {
    if (id) {
      setTitle("Editar Cliente");
      clientService
        .get(id)
        .then((response) => {
          const data = response.data;
          setRut(data.rut);
          setName(data.name);
          setEmail(data.email);
          setBirthDate(data.birthDate);
          setMonthlyVisits(data.monthlyVisits);
        })
        .catch((error) => {
          console.error("Error al obtener el cliente:", error);
        });
    }
  }, [id]);

  const saveClient = (e) => {
    e.preventDefault();
    const client = { rut, name, email, birthDate, monthlyVisits };

    if (id) {
      clientService
        .update(id, client)
        .then(() => {
          alert("Cliente actualizado correctamente");
          navigate("/clients");
        })
        .catch((error) => {
          console.error("Error al actualizar el cliente:", error);
        });
    } else {
      clientService
        .create(client)
        .then(() => {
          alert("Cliente creado correctamente");
          navigate("/clients");
        })
        .catch((error) => {
          console.error("Error al crear el cliente:", error);
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
            id="rut"
            label="Rut"
            value={rut}
            variant="standard"
            onChange={(e) => setRut(e.target.value)}
            helperText="Ej. 12345678-9"
          />
        </FormControl>

        <FormControl fullWidth margin="normal">
          <TextField
            id="name"
            label="Nombre"
            value={name}
            variant="standard"
            onChange={(e) => setName(e.target.value)}
          />
        </FormControl>

        <FormControl fullWidth margin="normal">
          <TextField
            id="email"
            label="Correo Electrónico"
            value={email}
            variant="standard"
            onChange={(e) => setEmail(e.target.value)}
          />
        </FormControl>

        <FormControl fullWidth margin="normal">
          <TextField
            id="birthDate"
            label="Fecha de Nacimiento"
            type="date"
            variant="standard"
            InputLabelProps={{ shrink: true }}
            value={birthDate}
            onChange={(e) => setBirthDate(e.target.value)}
          />
        </FormControl>

        <FormControl fullWidth margin="normal">
          <TextField
            id="monthlyVisits"
            label="Visitas Realizadas"
            type="number"
            variant="standard"
            value={monthlyVisits}
            onChange={(e) => setMonthlyVisits(e.target.value)}
          />
        </FormControl>

        <FormControl margin="normal">
          <Button
            variant="contained"
            color="primary"
            onClick={saveClient}
            startIcon={<SaveIcon />}
          >
            Grabar
          </Button>
        </FormControl>
      </form>
      <hr />
      <Link to="/clients">Volver a la Lista</Link>
    </Box>
  );
};

export default AddEditClient;
