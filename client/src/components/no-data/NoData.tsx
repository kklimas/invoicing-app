import * as React from 'react'
import Typography from '@mui/material/Typography'
import './NoData.css'
import noData from '../../resources/images/no-data.jpg'
import { Grid } from '@mui/material'

class NoDataProps {
     message: string
}

export const NoData = (props: NoDataProps) => {
     return <Grid container justifyContent='center' flexDirection='column' alignItems='center' className='NoData'>
          <img src={noData.toString()} className='NoDataImage' alt='No data found' />
          <Typography variant='h5'>{props.message}</Typography>
     </Grid>
}