import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import rateService from "../services/rate.service";
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
import MonetizationOnIcon from '@mui/icons-material/MonetizationOn';
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";

const Rates = () => {
  const [rates, setRates] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    rateService
      .getAll()
      .then((response) => {
        console.log(response.data);
        setRates(response.data);
      })
      .catch((error) => {
        console.error("Error al obtener tarifas:", error);
      });
  }, []);

  const handleDelete = (id) => {
    const confirmDelete = window.confirm("¿Está seguro que desea eliminar esta tarifa?");
    if (confirmDelete) {
      rateService
        .remove(id)
        .then(() => {
          setRates(rates.filter(rate => rate.id !== id));
        })
        .catch((error) => {
          console.error("Error al eliminar la tarifa:", error);
        });
    }
  };

  const handleEdit = (id) => {
    console.log("Printing id", id);
    navigate(`/rates/edit/${id}`);
  };

  return (
    <TableContainer component={Paper}>
      <div style={{ width: "100%", height: "100vh", padding: "20px", boxSizing: "border-box" }}>
        <Link to="/rates/add" style={{ textDecoration: "none", marginBottom: "1rem" }}>
          <Button variant="contained" color="primary" startIcon={<MonetizationOnIcon />}>
            Añadir Tarifa
          </Button>
        </Link>
        <br /> <br />
        <Table sx={{ width: "100%", minWidth: 650 }} size="small" aria-label="tabla de tarifas">
          <TableHead>
            <TableRow>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>
                #
              </TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>
                Vueltas
              </TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>
                Tiempo máximo de Vueltas (minutos)
              </TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>
                Precio
              </TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>
                Tiempo máximo de Reserva (minutos)
              </TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>
                Operaciones
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {rates.map((rate, index) => (
              <TableRow key={rate.id}>
                <TableCell align="left">{index + 1}</TableCell> {/* Number */}
                <TableCell align="left">{rate.laps}</TableCell>
                <TableCell align="left">{rate.maximumTimeLaps}</TableCell>
                <TableCell align="left">{rate.price}</TableCell>
                <TableCell align="left">{rate.maximumTimeReservation}</TableCell>
                <TableCell>
                  <Button
                    variant="contained"
                    color="info"
                    size="small"
                    onClick={() => handleEdit(rate.id)}
                    style={{ marginLeft: "0.5rem" }}
                    startIcon={<EditIcon />}
                  >
                    Editar
                  </Button>
                  <Button
                    variant="contained"
                    color="error"
                    size="small"
                    onClick={() => handleDelete(rate.id)}
                    style={{ marginLeft: "0.5rem" }}
                    startIcon={<DeleteIcon />}
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

export default Rates;

