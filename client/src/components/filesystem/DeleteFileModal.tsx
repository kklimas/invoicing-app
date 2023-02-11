import * as React from 'react'
import { FileModalProps, Transition } from './AddFileModal'
import { Button, CircularProgress, Dialog, DialogActions, DialogContent, DialogTitle, Grid } from '@mui/material'
import { useState } from 'react'
import FileSystemService from '../../services/FileSystemService'
import { ToastState } from '../../model/ToastState'

export const DeleteFileModal = (modalProps: FileModalProps) => {
     const [deleting, setDeleting] = useState(false)

     const handleFileDelete = () => {
          const file = modalProps.file;
          if (!file) return;

          setDeleting(true)
          FileSystemService.deleteFile(file.id)
               .then(() => {
                    modalProps.setMessage('Action invoked correctly')
                    modalProps.setState(ToastState.SUCCESS)
                    modalProps.getFiles()
               })
               .catch((err) => {
                    if (err.response && err.response.data && err.response.data.message) {
                         modalProps.setMessage(err.response.data.message);
                    } else {
                         modalProps.setMessage('Error occurred while uploading given file!');
                    }
                    modalProps.setState(ToastState.ERROR)
               })
               .finally(() => {
                    modalProps.setOpen(true)
                    setDeleting(false)
                    modalProps.handleClose()
               })
     }

     return (<Dialog
          open={modalProps.open}
          TransitionComponent={Transition}
          keepMounted
          onClose={modalProps.handleClose}
          aria-describedby='alert-dialog-slide-description'
     >
          <DialogTitle>
               Are you sure to delete {<b>{modalProps.file ? modalProps.file.name : ''}</b>}?
          </DialogTitle>
          <DialogContent>
               {deleting &&
                    <Grid container justifyContent={'center'}>
                         <CircularProgress /></Grid>}
          </DialogContent>
          <DialogActions>
               <Button disabled={deleting} onClick={modalProps.handleClose}>Cancel</Button>
               <Button disabled={deleting} onClick={handleFileDelete}>Delete</Button>
          </DialogActions>
     </Dialog>)
}