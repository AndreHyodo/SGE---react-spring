import React, {useEffect, useState} from "react";
import {fetchDocs} from "../../services/StatusService";
import axios from "axios";

const UploadFiles = () => {
    const [docs, setDocs] = useState([]);
    const [selectedFiles, setSelectedFiles] = useState([]);

    useEffect(() => {
        axios.get("/files/")
            .then(response => {
                setDocs(response.data);
            })
            .catch(error => {
                console.log(error);
            });
    }, [docs]);

    const handleFormSubmit = (event) => {
        event.preventDefault();
        const formData = new FormData();
        selectedFiles.forEach(file => {
            formData.append("file", file);
        });
        axios.post("/files/uploadFiles", formData)
            .then(response => {
                console.log(response);
                // Refresh the list of documents
                axios.get("/file/")
                    .then(response => {
                        setDocs(response.data);
                    })
                    .catch(error => {
                        console.log(error);
                    });
            })
            .catch(error => {
                console.log(error);
            });
    };

    const handleFileChange = (event) => {
        setSelectedFiles(event.target.files);
    };

    const handleDownloadClick = (fileId) => {
        axios.get(`/files/downloadFile/${fileId}`, {
            responseType: "arraybuffer"
        })
            .then(response => {
                const blob = new Blob([response.data], { type: response.headers["content-type"] });
                const link = document.createElement("a");
                link.href = URL.createObjectURL(blob);
                link.download = response.headers["content-disposition"].split("filename=\"")[1].split("\"")[0];
                link.click();
            })
            .catch(error => {
                console.log(error);
            });
    };

    return (
        <div>
            <div>
                <h3>Upload Multiple Files</h3>
                <form id="uploadFiles" onSubmit={handleFormSubmit}>
                    <input type="file" name="file" multiple required />
                    <button type="submit">Submit</button>
                </form>
            </div>
            <div>
                <h3>List of Documents</h3>
                <table>
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th>Name</th>
                        <th>Download Link</th>
                    </tr>
                    </thead>
                    <tbody>
                    {docs.map((doc) => (
                        <tr key={doc.id}>
                            <td>{doc.Id}</td>
                            <td>{doc.docName}</td>
                            <td>
                                <a href="#" onClick={() => handleDownloadClick(doc.id)}>Download</a>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default UploadFiles;