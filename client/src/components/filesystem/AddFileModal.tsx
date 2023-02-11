import * as React from 'react'
import { ChangeEvent, useState } from 'react'
import { Button, CircularProgress, Dialog, DialogActions, DialogContent, DialogTitle, Grid, Slide } from '@mui/material'
import { TransitionProps } from '@mui/material/transitions'
import FileSystemService from '../../services/FileSystemService'
import { ToastState } from '../../model/ToastState'
import { StorageFile } from '../../model/StorageFile'

export class FileModalProps {
     open: boolean
     file?: StorageFile
     getFiles: () => void
     setMessage: (message: string) => void
     setOpen: (open: boolean) => void
     setState: (state: ToastState) => void
     handleClose: () => void
}

export const Transition = React.forwardRef(function Transition(
     props: TransitionProps & {
          children: React.ReactElement<any, any>;
     },
     ref: React.Ref<unknown>,
) {
     return <Slide direction='up' ref={ref} {...props} />
})

export const AddFileModal = (modalProps: FileModalProps) => {
     const [currentFile, setCurrentFile] = useState<File>()
     const [uploading, setUploading] = useState(false)

     const handleFileChange = (event: ChangeEvent<HTMLInputElement>) => {
          const files = event.target.files
          setCurrentFile(files?.[0])
     }

     const handleFileUpload = () => {
          setUploading(true)
          if (!currentFile) return;

          FileSystemService.upload(currentFile)
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
                    setCurrentFile(undefined);
                    setUploading(false)
                    modalProps.handleClose()
               });
     }

     return (<Dialog
          open={modalProps.open}
          TransitionComponent={Transition}
          fullWidth={true}
          keepMounted
          onClose={modalProps.handleClose}
          aria-describedby='alert-dialog-slide-description'
     >
          <DialogTitle>
               Choose a csv file and save it
          </DialogTitle>
          <DialogContent>
               {uploading &&
                    <Grid container justifyContent={'center'}>
                         <CircularProgress /></Grid>}
          </DialogContent>
          <DialogActions>
               <Grid container justifyContent='space-between'>
                    <Grid item>
                         <Button disabled={uploading} variant='contained' component='label'>
                              Upload
                              <input hidden accept='text/csv' multiple type='file'
                                     onChange={handleFileChange} />
                         </Button>

                         {currentFile && currentFile.name}
                    </Grid>
                    <Grid item>
                         <Button disabled={uploading} onClick={modalProps.handleClose}>Cancel</Button>
                         <Button disabled={uploading || !currentFile} onClick={handleFileUpload}>Save</Button>
                    </Grid>
               </Grid>

          </DialogActions>
     </Dialog>)
}