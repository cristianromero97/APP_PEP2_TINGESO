import React, { useState, useEffect } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import {
  Box,
  TextField,
  Button,
  Typography,
  FormControl,
} from '@mui/material';
import SaveIcon from '@mui/icons-material/Save';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import dayjs from 'dayjs';
import discountDayService from '../services/discountday.service';

const AddEditDiscountDay = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [clientId, setClientId] = useState('');
  const [percentage, setPercentage] = useState('');
  const [reservationDate, setReservationDate] = useState(dayjs());
  const [title, setTitle] = useState('Nuevo Descuento por Día');

  useEffect(() => {
    if (id) {
      setTitle('Editar Descuento por Día');
      discountDayService
        .get(id)
        .then((response) => {
          const { clientId, percentage, reservationDate } = response.data;
          setClientId(clientId);
          setPercentage(percentage);
          setReservationDate(dayjs(reservationDate));
        })
        .catch((err) => {
          console.error('Error al obtener el descuento por día:', err);
        });
    }
  }, [id]);

  const saveDiscountDay = (e) => {
    e.preventDefault();
    const discountDay = {
      clientId: parseInt(clientId),
      percentage: parseFloat(percentage),
      reservationDate: reservationDate.format('YYYY-MM-DD'),
    };

    const action = id
      ? discountDayService.update(id, discountDay)
      : discountDayService.create(discountDay);

    action
      .then(() => {
        alert(`Descuento por día ${id ? 'actualizado' : 'creado'} correctamente`);
        navigate('/discount-day');
      })
      .catch((err) => {
        console.error(`Error al ${id ? 'actualizar' : 'crear'} el descuento por día:`, err);
      });
  };

  return (
    <Box
      display="flex"
      flexDirection="column"
      alignItems="center"
      justifyContent="center"
      component="form"
      sx={{ width: '100%', maxWidth: 600, margin: '0 auto', padding: '20px' }}
    >
      <Typography variant="h5">{title}</Typography>
      <hr />
      <FormControl fullWidth margin="normal">
        <TextField
          id="clientId"
          label="ID del Cliente"
          type="number"
          value={clientId}
          onChange={(e) => setClientId(e.target.value)}
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

      <FormControl fullWidth margin="normal">
        <LocalizationProvider dateAdapter={AdapterDayjs}>
          <DatePicker
            label="Fecha de Reserva"
            value={reservationDate}
            onChange={(newValue) => setReservationDate(newValue)}
            slotProps={{
              textField: { variant: 'standard', fullWidth: true },
            }}
          />
        </LocalizationProvider>
      </FormControl>

      <FormControl margin="normal">
        <Button
          variant="contained"
          color="primary"
          onClick={saveDiscountDay}
          startIcon={<SaveIcon />}
        >
          Grabar
        </Button>
      </FormControl>

      <hr />
      <Link to="/discount-day" style={{ textDecoration: 'none' }}>
        Volver a la Lista
      </Link>
    </Box>
  );
};

export default AddEditDiscountDay;
