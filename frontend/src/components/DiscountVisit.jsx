import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import discountVisitService from "../services/discountvisit.service";
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
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import LocalOfferIcon from "@mui/icons-material/LocalOffer";

const DiscountVisit = () => {
  const [discounts, setDiscounts] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    setLoading(true);
    discountVisitService
      .getAll()
      .then((res) => {
        setDiscounts(res.data);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Error al obtener los descuentos por visita:", err);
        setLoading(false);
      });
  }, []);

  const handleDelete = async (id) => {
    const confirmDelete = window.confirm("¿Estás seguro de que deseas eliminar este descuento?");
    if (confirmDelete) {
      try {
        await discountVisitService.remove(id);
        setDiscounts(discounts.filter((d) => d.id !== id));
      } catch (err) {
        console.error("Error al eliminar el descuento:", err);
      }
    }
  };

  const handleEdit = (id) => {
    setLoading(true);
    navigate(`/discount-visit/edit/${id}`);
    setLoading(false);
  };

  return (
    <TableContainer component={Paper}>
      <div style={{ width: "100%", height: "100vh", padding: "20px", boxSizing: "border-box" }}>
        <Button
          variant="contained"
          color="primary"
          onClick={() => navigate("/discount-visit/add")}
          startIcon={<LocalOfferIcon />}
          style={{ marginBottom: "1rem" }}
        >
          Agregar Descuento
        </Button>

        {loading ? (
          <div>Cargando...</div>
        ) : (
          <Table sx={{ width: "100%", minWidth: 650 }} size="small" aria-label="tabla de descuentos por visita">
            <TableHead>
              <TableRow>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>#</TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>ID Cliente</TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>Descuento (%)</TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>Operaciones</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {discounts.map((discount, index) => (
                <TableRow key={discount.id}>
                  <TableCell align="left">{index + 1}</TableCell>
                  <TableCell align="left">{discount.clientId}</TableCell>
                  <TableCell align="left">{discount.percentage}</TableCell>
                  <TableCell align="left">
                    <Button
                      variant="contained"
                      color="info"
                      size="small"
                      onClick={() => handleEdit(discount.id)}
                      startIcon={<EditIcon />}
                      style={{ marginLeft: "0.5rem" }}
                    >
                      Editar
                    </Button>
                    <Button
                      variant="contained"
                      color="error"
                      size="small"
                      onClick={() => handleDelete(discount.id)}
                      startIcon={<DeleteIcon />}
                      style={{ marginLeft: "0.5rem" }}
                    >
                      Eliminar
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        )}
      </div>
    </TableContainer>
  );
};

export default DiscountVisit;
