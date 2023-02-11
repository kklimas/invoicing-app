import * as React from 'react'
import { Alert, AlertColor, Snackbar } from '@mui/material'
import { ToastState } from '../model/ToastState'

class ToastProps {
     open: boolean
     setOpen: (open: boolean) => void
     message: string
     state: ToastState
}

const stateToString = (state: ToastState): AlertColor => {
     switch (state) {
          case ToastState.SUCCESS:
               return 'success'
          case ToastState.ERROR:
               return 'error'
          case ToastState.WARNING:
               return 'warning'
          default:
               return 'info'
     }
}

export const Toast = (props: ToastProps) => {
     const [open, setOpen, message, state] = [props.open, props.setOpen, props.message, props.state]
     const hideToast = () => {
          setOpen(false)
     }
     return (<Snackbar open={open} onClose={hideToast} autoHideDuration={3000}>
          <Alert severity={stateToString(state)}>{message}</Alert>
     </Snackbar>)
}