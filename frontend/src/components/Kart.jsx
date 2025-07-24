import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import kartService from "../services/kart.service"; 
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
import DirectionsCarIcon from "@mui/icons-material/DirectionsCar"; 
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";

const Karts = () => {
  const [karts, setKarts] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    kartService
      .getAll()
      .then((response) => {
        console.log(response.data);
        setKarts(response.data);
      })
      .catch((error) => {
        console.error("Error al obtener karts:", error);
      });
  }, []);

  const handleDelete = (id) => {
    const confirmDelete = window.confirm("¿Está seguro que desea eliminar este kart?");
    if (confirmDelete) {
      kartService
        .remove(id)
        .then(() => {
          setKarts(karts.filter(kart => kart.id !== id));
        })
        .catch((error) => {
          console.error("Error al eliminar el kart:", error);
        });
    }
  };
  
  const handleEdit = (id) => {
    console.log("Printing id", id);
    navigate(`/kart/edit/${id}`);
  };

  return (
    <TableContainer component={Paper}>
      <div style={{ width: "100%", height: "100vh", padding: "20px", boxSizing: "border-box" }}>
        <Link to="/karts/add" style={{ textDecoration: "none", marginBottom: "1rem" }}>
          <Button variant="contained" color="primary" startIcon={<DirectionsCarIcon />}>
            Añadir Kart
          </Button>
        </Link>
        <br /> <br />
        <Table sx={{ width: "100%", minWidth: 650 }} size="small" aria-label="tabla de karts">
          <TableHead>
            <TableRow>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>
                #
              </TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>
                Modelo
              </TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>
                Estado
              </TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>
                Operaciones
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {karts.map((kart, index) => (
              <TableRow key={kart.id}>
                <TableCell align="left">{index + 1}</TableCell>
                <TableCell align="left">{kart.model}</TableCell>
                <TableCell align="left">{kart.available}</TableCell>
          
                <TableCell>
                  <Button
                    variant="contained"
                    color="info"
                    size="small"
                    onClick={() => handleEdit(kart.id)}
                    style={{ marginLeft: "0.5rem" }}
                    startIcon={<EditIcon />}
                  >
                    Editar
                  </Button>
                  <Button
                    variant="contained"
                    color="error"
                    size="small"
                    onClick={() => handleDelete(kart.id)}
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

export default Karts;