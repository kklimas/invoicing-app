import React from 'react';
import './App.css';
import { Navbar } from './components/navigation/navbar/Navbar';
import { FileList } from './components/filesystem/FileList'
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import { Paper } from '@mui/material';
import Box from "@mui/material/Box";

function App() {
  return (
      <Router>
        <Navbar/>
        <Box p={8}>
            <Routes>
                <Route path="/" element={<Navigate to="/files" />} />
                <Route
                    path="/files"
                    element={<Paper elevation={3}>
                        <FileList />
                    </Paper>}
                />
                <Route
                    path="/pricing"
                    element={<Paper elevation={3}>
                        <FileList />
                    </Paper>}
                />
            </Routes>
        </Box>

      </Router>
  );
}

export default App;
