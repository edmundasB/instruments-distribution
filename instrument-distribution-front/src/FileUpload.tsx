import React from 'react'
import axios from 'axios';
import ReactDOM from 'react-dom';
import {Input, Button} from "@material-ui/core";
import App from "./App";

class FileUpload extends React.Component {

    // @ts-ignore
    constructor(props){
        // @ts-ignore
        super(props);
        this.state = {
            selectedFile:'',
        }

        this.handleInputChange = this.handleInputChange.bind(this);
    }

    handleInputChange(event: { target: { files: any[]; }; }) {
        this.setState({
            selectedFile: event.target.files[0],
        })
    }

    submit(){
        const data = new FormData()
        // @ts-ignore
        data.append('ledgerCsv', this.state.selectedFile)

        axios.post("http://localhost:8081/v1/upload", data, {
        }).then(res => {
            ReactDOM.render(<App />, document.getElementById('root'));
        })
    }

    render(){
        return(
             <div >
                 <Input color="secondary" variant="contained" component="span" type="file" name="upload_file"
                     // @ts-ignore
                        onChange={this.handleInputChange}>
                     Upload transactions data
                 </Input>
                 <Button type="submit" variant="contained" onClick={()=>this.submit()}>Load data</Button>
            </div>
        )
    }
}

export default FileUpload;
