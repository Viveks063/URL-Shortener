package com.example.urlshortener;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ViewController {

    @GetMapping("/")
    @ResponseBody // This is important - it tells Spring to return the string directly
    public String serveHomePage() {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>URL Shortener</title>
                    <style>
                        body {
                            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif;
                            display: flex;
                            justify-content: center;
                            align-items: center;
                            height: 100vh;
                            margin: 0;
                            background-color: #f4f7f6;
                            color: #333;
                        }
                        .container {
                            text-align: center;
                            background: white;
                            padding: 40px;
                            border-radius: 10px;
                            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
                            width: 90%;
                            max-width: 500px;
                        }
                        h1 {
                            color: #2c3e50;
                            margin-bottom: 20px;
                        }
                        .input-group {
                            display: flex;
                            gap: 10px;
                            margin-bottom: 20px;
                        }
                        #longUrlInput {
                            flex-grow: 1;
                            padding: 15px;
                            border: 1px solid #ccc;
                            border-radius: 5px;
                            font-size: 16px;
                        }
                        #shortenBtn {
                            padding: 15px 25px;
                            border: none;
                            background-color: #3498db;
                            color: white;
                            border-radius: 5px;
                            cursor: pointer;
                            font-size: 16px;
                            transition: background-color 0.3s;
                        }
                        #shortenBtn:hover {
                            background-color: #2980b9;
                        }
                        #result {
                            margin-top: 20px;
                            background-color: #ecf0f1;
                            padding: 15px;
                            border-radius: 5px;
                            word-wrap: break-word;
                            min-height: 20px;
                        }
                        #result a {
                            color: #2980b9;
                            text-decoration: none;
                            font-weight: bold;
                        }
                        #error {
                            color: #e74c3c;
                            margin-top: 15px;
                        }
                    </style>
                </head>
                <body>

                <div class="container">
                    <h1>URL Shortener</h1>
                    <p>Enter a long URL to make it short!</p>
                    <div class="input-group">
                        <input type="url" id="longUrlInput" placeholder="https://example.com/very/long/url">
                        <button id="shortenBtn">Shorten</button>
                    </div>
                    <div id="result"></div>
                    <p id="error"></p>
                </div>

                <script>
                   const longUrlInput = document.getElementById('longUrlInput');
                   const shortenBtn = document.getElementById('shortenBtn');
                   const resultDiv = document.getElementById('result');
                   const errorP = document.getElementById('error');

                   shortenBtn.addEventListener('click', async () => {
                       const longUrl = longUrlInput.value;

                       if (!longUrl || !longUrl.startsWith('http')) {
                           errorP.textContent = 'Please enter a valid URL (starting with http or https).';
                           resultDiv.innerHTML = '';
                           return;
                       }

                       errorP.textContent = '';
                       resultDiv.innerHTML = 'Shortening...';

                       try {
                           const response = await fetch('/shorten', {
                               method: 'POST',
                               headers: {
                                   'Content-Type': 'text/plain'
                               },
                               body: longUrl
                           });

                           if (response.ok) {
                               const shortUrl = await response.text();
                               resultDiv.innerHTML = `Your short URL is: <a href="${shortUrl}" target="_blank">${shortUrl}</a>`;
                           } else {
                               errorP.textContent = 'Failed to shorten URL. The server responded with an error.';
                               resultDiv.innerHTML = '';
                           }
                       } catch (err) {
                           console.error('Error:', err);
                           errorP.textContent = 'An error occurred. Please check your connection and if the server is running.';
                           resultDiv.innerHTML = '';
                       }
                   });
                </script>

                </body>
                </html>
                """;
    }
}