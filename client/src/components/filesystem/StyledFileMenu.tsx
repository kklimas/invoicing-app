import * as React from 'react'
import { useState } from 'react'
import Menu, { MenuProps } from '@mui/material/Menu'
import { alpha, Divider, styled } from '@mui/material'
import MenuItem from '@mui/material/MenuItem'
import MoreVertIcon from '@mui/icons-material/MoreVert'
import FileDownloadIcon from '@mui/icons-material/FileDownload'
import MoreHorizIcon from '@mui/icons-material/MoreHoriz'
import ClearIcon from '@mui/icons-material/Clear'
import IconButton from '@mui/material/IconButton'
import { StorageFile } from '../../model/StorageFile'

class StyledMenuProps {
     row: StorageFile
     handleDeleteFile: (row: StorageFile) => void
}

const StyledMenu = styled((props: MenuProps) => (
     <Menu
          elevation={0}
          anchorOrigin={{
               vertical: 'bottom',
               horizontal: 'right',
          }}
          transformOrigin={{
               vertical: 'top',
               horizontal: 'right',
          }}
          {...props}
     />
))(({ theme }) => ({
     '& .MuiPaper-root': {
          borderRadius: 6,
          marginTop: theme.spacing(1),
          minWidth: 150,
          color:
               theme.palette.mode === 'light' ? 'rgb(55, 65, 81)' : theme.palette.grey[300],
          boxShadow:
               'rgb(255, 255, 255) 0px 0px 0px 0px, rgba(0, 0, 0, 0.05) 0px 0px 0px 1px, rgba(0, 0, 0, 0.1) 0px 10px 15px -3px, rgba(0, 0, 0, 0.05) 0px 4px 6px -2px',
          '& .MuiMenu-list': {
               padding: '4px 0',
          },
          '& .MuiMenuItem-root': {
               '& .MuiSvgIcon-root': {
                    fontSize: 18,
                    color: theme.palette.text.secondary,
                    marginRight: theme.spacing(1.5),
               },
               '&:active': {
                    backgroundColor: alpha(
                         theme.palette.primary.main,
                         theme.palette.action.selectedOpacity,
                    ),
               },
          },
     },
}))

export const StyledFileMenu = (props: StyledMenuProps) => {
     const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null)
     const open = Boolean(anchorEl)
     const handleClick = (event: React.MouseEvent<HTMLElement>) => {
          setAnchorEl(event.currentTarget)
     }
     const handleClose = () => {
          setAnchorEl(null)
     }

     const handleFileSave = () => {
          handleClose()
          fetch(props.row.url).then(response => {
               response.blob().then(blob => {
                    const fileURL = window.URL.createObjectURL(blob)
                    const alink = document.createElement('a')
                    alink.href = fileURL
                    alink.download = `${props.row.name}.csv`
                    alink.click()
               })
          })
     }

     const handleDeleteFile = () => {
          handleClose()
          props.handleDeleteFile(props.row)
     }

     return (
          <div>
               <IconButton
                    aria-label='more'
                    id='long-button'
                    aria-controls={open ? 'long-menu' : undefined}
                    aria-expanded={open ? 'true' : undefined}
                    aria-haspopup='true'
                    onClick={handleClick}
               >
                    <MoreVertIcon />
               </IconButton>
               <StyledMenu
                    id='customized-menu'
                    MenuListProps={{
                         'aria-labelledby': 'customized-button',
                    }}
                    anchorEl={anchorEl}
                    open={open}
                    onClose={handleClose}
               >
                    <MenuItem onClick={handleFileSave} disableRipple>
                         <FileDownloadIcon />
                         Download
                    </MenuItem>
                    <MenuItem onClick={handleClose} disableRipple>
                         <MoreHorizIcon />
                         More
                    </MenuItem>
                    <Divider sx={{ my: 0.5 }} />
                    <MenuItem onClick={handleDeleteFile} disableRipple>
                         <ClearIcon />
                         Delete
                    </MenuItem>
               </StyledMenu>
          </div>
     )
}
