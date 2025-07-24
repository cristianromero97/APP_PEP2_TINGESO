import React, { useState, useEffect } from "react";
import { useParams, useNavigate, Link } from "react-router-dom";
import reportService from "../services/report.service";
import {
  Box,
  TextField,
  Button,
  FormControl,
  MenuItem,
} from "@mui/material";
import SaveIcon from "@mui/icons-material/Save";

const AddEditReport = () => {
  const [type, setType] = useState("");
  const [dateGeneration, setDateGeneration] = useState("");
  const [totalRevenue, setTotalRevenue] = useState("");
  const [totalPeople, setTotalPeople] = useState("");
  const [totalLaps, setTotalLaps] = useState("");
  const [averagePerPerson, setAveragePerPerson] = useState("");
  const [averagePerLaps, setAveragePerLaps] = useState("");

  const { id } = useParams();
  const navigate = useNavigate();
  const [title, setTitle] = useState("Nuevo Reporte");

  useEffect(() => {
    if (id) {
      setTitle("Editar Reporte");
      reportService
        .get(id)
        .then((response) => {
          const data = response.data;
          setType(data.type);
          setDateGeneration(data.dateGeneration);
          setTotalRevenue(data.totalRevenue);
          setTotalPeople(data.totalPeople);
          setTotalLaps(data.totalLaps);
          setAveragePerPerson(data.averagePerPerson);
          setAveragePerLaps(data.averagePerLaps);
        })
        .catch((error) => {
          console.error("Error al obtener el reporte:", error);
        });
    }
  }, [id]);

  const saveReport = (e) => {
    e.preventDefault();
    const report = {
      type,
      dateGeneration,
      totalRevenue,
      totalPeople,
      totalLaps,
      averagePerPerson,
      averagePerLaps,
    };

    const saveFn = id ? reportService.update(id, report) : reportService.create(report);

    saveFn
      .then(() => {
        alert("Reporte guardado correctamente");
        navigate("/reports");
      })
      .catch((error) => {
        console.error("Error al guardar el reporte:", error);
      });
  };

  return (
    <Box
      display="flex"
      flexDirection="column"
      alignItems="center"
      justifyContent="center"
    >
      <h3>{title}</h3>
      <form>
        <FormControl fullWidth margin="normal">
          <TextField
            select
            label="Tipo"
            value={type}
            onChange={(e) => setType(e.target.value)}
            variant="standard"
          >
            <MenuItem value="Tiempo">Time</MenuItem>
            <MenuItem value="Vueltas">Laps</MenuItem>
            <MenuItem value="Persona">People</MenuItem>
          </TextField>
        </FormControl>

        <FormControl fullWidth margin="normal">
          <TextField
            label="Fecha de Generación"
            type="date"
            variant="standard"
            InputLabelProps={{ shrink: true }}
            value={dateGeneration}
            onChange={(e) => setDateGeneration(e.target.value)}
          />
        </FormControl>

        <FormControl fullWidth margin="normal">
          <TextField
            label="Ingreso Total"
            type="number"
            variant="standard"
            value={totalRevenue}
            onChange={(e) => setTotalRevenue(e.target.value)}
          />
        </FormControl>

        <FormControl fullWidth margin="normal">
          <TextField
            label="Total de Personas"
            type="number"
            variant="standard"
            value={totalPeople}
            onChange={(e) => setTotalPeople(e.target.value)}
          />
        </FormControl>

        <FormControl fullWidth margin="normal">
          <TextField
            label="Total de Vueltas"
            type="number"
            variant="standard"
            value={totalLaps}
            onChange={(e) => setTotalLaps(e.target.value)}
          />
        </FormControl>

        <FormControl fullWidth margin="normal">
          <TextField
            label="Promedio por Persona"
            type="number"
            variant="standard"
            value={averagePerPerson}
            onChange={(e) => setAveragePerPerson(e.target.value)}
          />
        </FormControl>

        <FormControl fullWidth margin="normal">
          <TextField
            label="Promedio por Vuelta"
            type="number"
            variant="standard"
            value={averagePerLaps}
            onChange={(e) => setAveragePerLaps(e.target.value)}
          />
        </FormControl>

        <FormControl margin="normal">
          <Button
            variant="contained"
            color="primary"
            onClick={saveReport}
            startIcon={<SaveIcon />}
          >
            Grabar
          </Button>
        </FormControl>
      </form>
      <hr />
      <Link to="/reports">Volver a la Lista</Link>
    </Box>
  );
};

export default AddEditReport;
