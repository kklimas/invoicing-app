import * as React from 'react'
import { useEffect, useState } from 'react'
import Table from '@mui/material/Table'
import TableBody from '@mui/material/TableBody'
import TableCell from '@mui/material/TableCell'
import TableContainer from '@mui/material/TableContainer'
import TableHead from '@mui/material/TableHead'
import TablePagination from '@mui/material/TablePagination'
import TableRow from '@mui/material/TableRow'
import Typography from '@mui/material/Typography'
import { AxiosResponse } from 'axios'
import { StorageFile } from '../../model/StorageFile'
import { Button, CircularProgress } from '@mui/material'
import { AddFileModal } from './AddFileModal'
import FileSystemService from '../../services/FileSystemService'
import { Toast } from '../Toast'
import { ToastState } from '../../model/ToastState'
import { DeleteFileModal } from './DeleteFileModal'
import Box from '@mui/material/Box'
import AddIcon from '@mui/icons-material/Add'
import { NoData } from '../no-data/NoData'
import { StyledFileMenu } from './StyledFileMenu'

export interface Column {
     id: 'name' | 'size' | 'storageDate' | 'details';
     label: string;
     minWidth?: number;
     isMenu?: boolean;
     align?: 'center';
}

const columns: readonly Column[] = [
     { id: 'name', label: 'Name', minWidth: 170 },
     { id: 'size', label: 'Size', minWidth: 100 },
     { id: 'storageDate', label: 'Storage date', minWidth: 150 },
     {
          id: 'details',
          label: 'More',
          minWidth: 100,
          align: 'center',
          isMenu: true,
     },
]

export const FileList = () => {
     const [page, setPage] = useState(0)
     const [rowsPerPage, setRowsPerPage] = useState(10)
     const [files, setFiles] = useState<StorageFile[]>([])
     const [loading, setLoading] = useState(false)

     const [fileToDelete, setFileToDelete] = useState<StorageFile>()

     // modals
     const [open, setOpen] = useState(false)
     const [openDelete, setOpenDelete] = useState(false)

     // toast
     const [toastOpen, setToastOpen] = useState(false)
     const [toastMessage, setToastMessage] = useState('')
     const [toastState, setToastState] = useState(ToastState.INFO)

     useEffect(() => {
          getFiles()
     }, [])

     const getFiles = () => {
          setLoading(true)
          FileSystemService.getFiles()
               .then((res: AxiosResponse) => {
                    setFiles(res.data)
               })
               .catch(err => {
                    setToastState(ToastState.ERROR)
                    setToastMessage(err.message)
                    setToastOpen(true)
               })
               .finally(() => setLoading(false))
     }

     const handleClickOpen = () => {
          setOpen(true)
     }

     const handleChangePage = (event: unknown, newPage: number) => {
          setPage(newPage)
     }

     const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
          setRowsPerPage(+event.target.value)
          setPage(0)
     }

     const handleDeleteFile = (file: StorageFile) => {
          setFileToDelete(file)
          setOpenDelete(true)
     }

     const extractTableFieldValue = (row: StorageFile, column: Column) => {
          const value = column.id !== 'details' ? row[column.id] : ''
          return (<TableCell key={column.id} align={column.align}>
               {column.isMenu
                    ? (<StyledFileMenu row={row} handleDeleteFile={() => handleDeleteFile(row)} />)
                    : value}
          </TableCell>)
     }

     return (<>
          <Typography
               sx={{ flex: '1 1 100%' }}
               variant='h3'
               id='tableTitle'
          >
               Files
          </Typography>
          <TableContainer sx={{ maxHeight: 440 }}>
               <Table stickyHeader aria-label='sticky table'>
                    <TableHead>
                         <TableRow>
                              {columns.map((column) => (
                                   <TableCell
                                        key={column.id}
                                        align={column.align}
                                        style={{ minWidth: column.minWidth }}
                                   >
                                        {column.label}
                                   </TableCell>
                              ))}
                         </TableRow>
                    </TableHead>
                    <TableBody>
                         {!loading && files
                              .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                              .map((row) => {
                                   return (
                                        <TableRow hover role='checkbox' tabIndex={-1} key={row.id}>
                                             {columns.map((column) =>
                                                  extractTableFieldValue(row, column),
                                             )}
                                        </TableRow>
                                   )
                              })}
                    </TableBody>
               </Table>
          </TableContainer>
          {loading && <CircularProgress />}
          {files.length === 0 && <NoData message={'No files were found.'} />}
          {files.length !== 0 && <TablePagination
               rowsPerPageOptions={[5, 10, 20]}
               component='div'
               count={files.length}
               rowsPerPage={rowsPerPage}
               page={page}
               onPageChange={handleChangePage}
               onRowsPerPageChange={handleChangeRowsPerPage}
          />}

          <Box mt={4}>
               <Button onClick={handleClickOpen} variant='contained' startIcon={<AddIcon />}>
                    Add file
               </Button>
          </Box>
          <AddFileModal open={open} getFiles={getFiles} setOpen={setToastOpen} setMessage={setToastMessage}
                        setState={setToastState}
                        handleClose={() => setOpen(false)} />
          <DeleteFileModal open={openDelete} file={fileToDelete} getFiles={getFiles} setOpen={setToastOpen}
                           setMessage={setToastMessage}
                           setState={setToastState}
                           handleClose={() => setOpenDelete(false)} />
          <Toast open={toastOpen} setOpen={setToastOpen} message={toastMessage} state={toastState} />
     </>)
}
