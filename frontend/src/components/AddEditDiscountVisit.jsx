import React, { useEffect, useState } from "react";
import { useNavigate, useParams, Link } from "react-router-dom";
import discountVisitService from "../services/discountvisit.service";
import { Box, TextField, Button, FormControl, Typography } from "@mui/material";
import SaveIcon from "@mui/icons-material/Save";

const AddEditDiscountVisit = () => {
  const [discount, setDiscount] = useState({
    clientId: "",
    percentage: "",
  });

  const navigate = useNavigate();
  const { id } = useParams();
  const isEditing = Boolean(id);
  const [title, setTitle] = useState("Registrar Descuento por Visitas");

  useEffect(() => {
    if (isEditing) {
      setTitle("Editar Descuento por Visitas");
      discountVisitService.get(id).then((response) => {
        setDiscount(response.data);
      });
    }
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setDiscount({ ...discount, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      if (isEditing) {
        await discountVisitService.update(id, discount);
      } else {
        await discountVisitService.create(discount);
      }

      navigate("/discount-visit");
    } catch (error) {
      console.error("Error al guardar el descuento por visitas:", error);
    }
  };

  return (
    <Box
      display="flex"
      flexDirection="column"
      alignItems="center"
      justifyContent="center"
      component="form"
      onSubmit={handleSubmit}
      sx={{ width: "100%", maxWidth: 600, margin: "0 auto", padding: "20px" }}
    >
      <Typography variant="h5">{title}</Typography>
      <hr />
      <FormControl fullWidth margin="normal">
        <TextField
          id="clientId"
          label="ID del Cliente"
          name="clientId"
          value={discount.clientId}
          onChange={handleChange}
          variant="standard"
          required
        />
      </FormControl>

      <FormControl fullWidth margin="normal">
        <TextField
          id="percentage"
          label="Porcentaje de Descuento"
          name="percentage"
          value={discount.percentage}
          onChange={handleChange}
          type="number"
          inputProps={{ min: 0, max: 100 }}
          variant="standard"
          required
        />
      </FormControl>

      <FormControl margin="normal">
        <Button
          type="submit"
          variant="contained"
          color="primary"
          startIcon={<SaveIcon />}
        >
          Guardar
        </Button>
      </FormControl>

      <hr />
      <Link to="/discount-visit" style={{ textDecoration: "none" }}>
        Volver a la Lista
      </Link>
    </Box>
  );
};

export default AddEditDiscountVisit;
