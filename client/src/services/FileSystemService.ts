import http from '../config/http-commons'
import { AxiosResponse } from 'axios'

const CONTEXT_PATH = '/filesystem'

const upload = (file: File, onUploadProgress?: any): Promise<any> => {
     const formData = new FormData();

     formData.append('file', file);

     return http.post(`${CONTEXT_PATH}`, formData, {
          headers: {
               'Content-Type': 'multipart/form-data',
          },
          onUploadProgress,
     });
};

const getFiles = async (): Promise<AxiosResponse> => {
     return await http.get(`${CONTEXT_PATH}`)
}

const deleteFile = async (id: number): Promise<AxiosResponse> => {
     return await http.delete(`${CONTEXT_PATH}/${id}`)
}

const FileSystemService = {
     upload,
     getFiles,
     deleteFile
};

export default FileSystemService;
