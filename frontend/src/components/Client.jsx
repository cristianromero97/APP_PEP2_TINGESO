import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import clientService from "../services/client.service";
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
import PersonAddIcon from "@mui/icons-material/PersonAdd";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import EventIcon from "@mui/icons-material/Event";

const Clientes = () => {
  const [clientes, setClientes] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    clientService
      .getAll()
      .then((response) => {
        console.log(response.data);
        setClientes(response.data);
      })
      .catch((error) => {
        console.error("Error al obtener clientes:", error);
      });
  }, []);

  const handleDelete = (id) => {
    const confirmDelete = window.confirm("Primero asegúrese de borrar la(s) reservación(es) relacionada(s) antes de eliminar el cliente. ¿Está seguro que desea continuar?");
    if (confirmDelete) {
      clientService
        .remove(id)
        .then(() => {
          setClientes(clientes.filter(cliente => cliente.id !== id));
        })
        .catch((error) => {
          console.error("Error al eliminar el cliente:", error);
        });
    }
  };
  
  const handleEdit = (id) => {
    console.log("Printing id", id);
    navigate(`/client/edit/${id}`);
  };

  const handleReserve = (clientId) => {
    navigate(`/reservations/add?clientId=${clientId}`);
  };

  return (
    <TableContainer component={Paper}>
    <div style={{ width: "100%", height: "100vh", padding: "20px", boxSizing: "border-box" }}>
      <Link to="/clients/add" style={{ textDecoration: "none", marginBottom: "1rem" }}>
        <Button variant="contained" color="primary" startIcon={<PersonAddIcon />}>
          Añadir Cliente
        </Button>
      </Link>
      <br /> <br />
        <Table sx={{ width: "100%", minWidth: 650 }} size="small" aria-label="tabla de clientes">
          <TableHead>
            <TableRow>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>
                #
              </TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>
                Rut
              </TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>
                Nombre
              </TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>
               Correo Electrónico
              </TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>
                Fecha Nacimiento
              </TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold" }}>
                Visitas Realizadas
              </TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>
                Operaciones
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {clientes.map((cliente, index) => (
              <TableRow key={cliente.id}>
                <TableCell align="left">{index + 1}</TableCell> {/* Number */}
                <TableCell align="left">{cliente.rut}</TableCell>
                <TableCell align="left">{cliente.name}</TableCell>
                <TableCell align="left">{cliente.email}</TableCell>
                <TableCell align="left">{cliente.birthDate}</TableCell>
                <TableCell align="right">{cliente.monthlyVisits}</TableCell>
                <TableCell>
                  <Button
                    variant="contained"
                    color="info"
                    size="small"
                    onClick={() => handleEdit(cliente.id)}
                    style={{ marginLeft: "0.5rem" }}
                    startIcon={<EditIcon />}
                  >
                    Editar
                  </Button>
                  <Button
                    variant="contained"
                    color="error"
                    size="small"
                    onClick={() => handleDelete(cliente.id)}
                    style={{ marginLeft: "0.5rem" }}
                    startIcon={<DeleteIcon />}
                  >
                    Eliminar
                  </Button>

                  <Button
                    variant="contained"
                    color="primary"
                    size="small"
                    onClick={() => handleReserve(cliente.id)}  // Redirect to the booking page
                    style={{ marginLeft: "0.5rem" }}
                    startIcon={<EventIcon />}
                  >
                    Reservar
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

export default Clientes;
