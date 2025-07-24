import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import weeklyRackService from "../services/weeklyRack.service";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Button,
} from "@mui/material";

import CalendarMonthIcon from "@mui/icons-material/CalendarMonth";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";

const WeeklyRack = () => {
  const [racks, setRacks] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    weeklyRackService
      .getAll()
      .then((response) => setRacks(response.data))
      .catch((error) => console.error("Error al obtener los racks:", error));
  }, []);

  const handleDelete = (id) => {
    if (window.confirm("¿Seguro que desea eliminar este rack?")) {
      weeklyRackService
        .remove(id)
        .then(() => setRacks(racks.filter(r => r.id !== id)))
        .catch((error) => console.error("Error al eliminar rack:", error));
    }
  };

  const handleEdit = (id) => {
    navigate(`/weekly-rack/edit/${id}`);
  };

  return (
    <TableContainer component={Paper}>
      <div style={{ padding: "20px", height: "100vh", boxSizing: "border-box" }}>
        <Button
          variant="contained"
          color="primary"
          startIcon={<CalendarMonthIcon />}
          onClick={() => navigate("/weekly-rack/add")}
        >
          Añadir Rack
        </Button>
        <br /><br />
        <Table size="small" sx={{ minWidth: 650 }}>
          <TableHead>
            <TableRow>
              <TableCell sx={{ fontWeight: "bold" }}>#</TableCell>
              <TableCell sx={{ fontWeight: "bold" }}>Día</TableCell>
              <TableCell sx={{ fontWeight: "bold" }}>Bloque de Tiempo</TableCell>
              <TableCell sx={{ fontWeight: "bold" }}>ID Reservación</TableCell>
              <TableCell sx={{ fontWeight: "bold" }}>Operaciones</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {racks.map((rack, index) => (
              <TableRow key={rack.id}>
                <TableCell>{index + 1}</TableCell>
                <TableCell>{rack.day}</TableCell>
                <TableCell>{rack.timeBlock}</TableCell>
                <TableCell>{rack.reservationId != null ? rack.reservationId : "-"}</TableCell> 
                <TableCell>
                  <Button
                    variant="contained"
                    size="small"
                    color="info"
                    onClick={() => handleEdit(rack.id)}
                    startIcon={<EditIcon />}
                    sx={{ mr: 1, mb: 0.5 }}
                  >
                    Editar
                  </Button>
                  <Button
                    variant="contained"
                    size="small"
                    color="error"
                    onClick={() => handleDelete(rack.id)}
                    startIcon={<DeleteIcon />}
                    sx={{ mb: 0.5 }}
                  >
                    Eliminar
                  </Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </div>
    </TableContainer>
  );
};

export default WeeklyRack;
