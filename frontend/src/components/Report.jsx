import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import reportService from "../services/report.service";
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
import InsertChartIcon from "@mui/icons-material/InsertChart";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";

const Report = () => {
  const [reports, setReports] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    reportService
      .getAll()
      .then((response) => {
        setReports(response.data);
      })
      .catch((error) => {
        console.error("Error al obtener reportes:", error);
      });
  }, []);

  const handleDelete = (id) => {
    const confirmDelete = window.confirm("¿Está seguro que desea eliminar este reporte?");
    if (confirmDelete) {
      reportService
        .remove(id)
        .then(() => {
          setReports(reports.filter((report) => report.id !== id));
        })
        .catch((error) => {
          console.error("Error al eliminar el reporte:", error);
        });
    }
  };

  const handleEdit = (id) => {
    navigate(`/reports/edit/${id}`);
  };

  return (
    <TableContainer component={Paper}>
      <div style={{ padding: "20px", height: "100vh", boxSizing: "border-box" }}>
        <Link to="/reports/add" style={{ textDecoration: "none" }}>
          <Button variant="contained" color="primary" startIcon={<InsertChartIcon />}>
          Añadir Reporte
          </Button>
        </Link>
        <br /><br />
        <Table sx={{ minWidth: 650 }} size="small" aria-label="tabla de reportes">
          <TableHead>
            <TableRow>
              <TableCell sx={{ fontWeight: "bold" }}>#</TableCell>
              <TableCell sx={{ fontWeight: "bold" }}>Tipo</TableCell>
              <TableCell sx={{ fontWeight: "bold" }}>Fecha Generación</TableCell>
              <TableCell sx={{ fontWeight: "bold" }}>Ingreso Total</TableCell>
              <TableCell sx={{ fontWeight: "bold" }}>Personas</TableCell>
              <TableCell sx={{ fontWeight: "bold" }}>Vueltas/Tiempo</TableCell>
              <TableCell sx={{ fontWeight: "bold" }}>Promedio/Persona</TableCell>
              <TableCell sx={{ fontWeight: "bold" }}>Promedio/Vuelta</TableCell>
              <TableCell sx={{ fontWeight: "bold" }}>Operaciones</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {reports.map((report, index) => (
              <TableRow key={report.id}>
                <TableCell>{index + 1}</TableCell>
                <TableCell>{report.type}</TableCell>
                <TableCell>{report.dateGeneration}</TableCell>
                <TableCell>{report.totalRevenue}</TableCell>
                <TableCell>{report.totalPeople}</TableCell>
                <TableCell>{report.totalLaps}</TableCell>
                <TableCell>{report.averagePerPerson}</TableCell>
                <TableCell>{report.averagePerLaps}</TableCell>
                <TableCell>
                  <Button
                    size="small"
                    variant="contained"
                    color="info"
                    onClick={() => handleEdit(report.id)}
                    startIcon={<EditIcon />}
                    sx={{ mr: 1 }}
                  >
                    Editar
                  </Button>
                  <Button
                    size="small"
                    variant="contained"
                    color="error"
                    onClick={() => handleDelete(report.id)}
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

export default Report;
