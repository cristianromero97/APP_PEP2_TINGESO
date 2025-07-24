import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import discountService from "../services/discount.service";
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

const Discount = () => {
  const [discounts, setDiscounts] = useState([]);
  const [loading, setLoading] = useState(true); // New state
  const navigate = useNavigate();

  useEffect(() => {
    setLoading(true); // Start load
  
    discountService
      .getAll()
      .then((response) => {
        console.log("Descuentos recibidos:", response.data);  // Verification of the response
        setDiscounts(response.data);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Error al obtener los descuentos:", err);
        setLoading(false);
      });
  }, []);

  const handleDelete = (id) => {
    const confirmDelete = window.confirm("¿Está seguro que desea eliminar este descuento?");
    if (confirmDelete) {
      discountService
        .remove(id)
        .then(() => {
          console.log(`Descuento ${id} eliminado`);
          setDiscounts(discounts.filter((discount) => discount.id !== id));
        })
        .catch((err) => {
          console.error("Error al eliminar el descuento:", err);
        });
    }
  };

  const handleEdit = (id) => {
    setLoading(true);
    navigate(`/discounts/edit/${id}`);
    setLoading(false);
  };

  return (
    <TableContainer component={Paper}>
      <div style={{ width: "100%", height: "100vh", padding: "20px", boxSizing: "border-box" }}>
        <Button
          variant="contained"
          color="primary"
          onClick={() => navigate("/discounts/add")}
          startIcon={<LocalOfferIcon />}
          style={{ marginBottom: "1rem" }}
        >
          Agregar Descuento
        </Button>
        
        {/* Send to message to load if is loading */}
        {loading ? (
          <div>Cargando...</div>
        ) : (
          <Table sx={{ width: "100%", minWidth: 650 }} size="small" aria-label="tabla de descuentos">
            <TableHead>
            <TableRow>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>
                #
              </TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                  Min. Personas
                </TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                  Max. Personas
                </TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                  Descuento (%)
                </TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                  Operaciones
                </TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {discounts.map((discount, index) => (
                <TableRow key={discount.id}>
                  <TableCell align="left">{index + 1}</TableCell> {/* Number*/}
                  <TableCell align="left">{discount.minPerson}</TableCell>
                  <TableCell align="left">{discount.maxPerson}</TableCell>
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

export default Discount;
