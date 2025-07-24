import React, { useEffect, useState } from "react";
import { useParams, useNavigate, Link } from "react-router-dom";
import discountService from "../services/discount.service";
import { Box, TextField, Button, FormControl, Grid, Typography } from "@mui/material";
import SaveIcon from "@mui/icons-material/Save";

const AddEditDiscount = () => {
  const [minPerson, setMinPerson] = useState('');
  const [maxPerson, setMaxPerson] = useState('');
  const [percentage, setPercentage] = useState('');
  const { id } = useParams();
  const navigate = useNavigate();
  const [title, setTitle] = useState('Nuevo Descuento');

  useEffect(() => {
    if (id) {
      setTitle('Editar Descuento');
      discountService
        //.getDiscountById(id)
        .get(id)
        .then((response) => {
          const { minPerson, maxPerson, percentage } = response.data;
          setMinPerson(minPerson);
          setMaxPerson(maxPerson);
          setPercentage(percentage);
        })
        .catch((err) => {
          console.error('Error al obtener el descuento:', err);
        });
    }
  }, [id]);

  const saveDiscount = (e) => {
    e.preventDefault();
    const discount = { minPerson, maxPerson, percentage };

    if (id) {
      discountService
        .updateDiscount(id, discount)
        .then(() => {
          alert('Descuento actualizado correctamente');
          navigate('/discounts');
        })
        .catch((err) => {
          console.error('Error al actualizar el descuento:', err);
        });
    } else {
      discountService
        .createDiscount(discount)
        .then(() => {
          alert('Descuento creado correctamente');
          navigate('/discounts');
        })
        .catch((err) => {
          console.error('Error al crear el descuento:', err);
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
      sx={{ width: "100%", maxWidth: 600, margin: "0 auto", padding: "20px" }}
    >
      <Typography variant="h5">{title}</Typography>
      <hr />
      <FormControl fullWidth margin="normal">
        <TextField
          id="minPerson"
          label="Cantidad mínima de personas"
          type="number"
          value={minPerson}
          onChange={(e) => setMinPerson(e.target.value)}
          variant="standard"
          required
        />
      </FormControl>

      <FormControl fullWidth margin="normal">
        <TextField
          id="maxPerson"
          label="Cantidad máxima de personas"
          type="number"
          value={maxPerson}
          onChange={(e) => setMaxPerson(e.target.value)}
          variant="standard"
          required
        />
      </FormControl>

      <FormControl fullWidth margin="normal">
        <TextField
          id="percentage"
          label="Porcentaje de descuento"
          type="number"
          value={percentage}
          onChange={(e) => setPercentage(e.target.value)}
          variant="standard"
          required
        />
      </FormControl>

      <FormControl margin="normal">
        <Button
          variant="contained"
          color="primary"
          onClick={saveDiscount}
          startIcon={<SaveIcon />}
        >
          Grabar
        </Button>
      </FormControl>

      <hr />
      <Link to="/discounts" style={{ textDecoration: "none" }}>
        Volver a la Lista
      </Link>
    </Box>
  );
};

export default AddEditDiscount;
