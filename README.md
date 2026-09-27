# StudyLens AI

## AI-Powered Personal Study Assistant

StudyLens AI is a privacy-focused desktop application that helps students understand and revise their study material using local AI.

Users can upload a PDF and use AI-powered features such as question answering, summarization, key topic extraction, and interactive quizzes.

## Features

- Upload and extract text from PDF study material
- Ask questions about uploaded notes
- Generate concise summaries
- Identify important topics and concepts
- Generate interactive MCQ quizzes
- Automatic quiz scoring
- Local AI inference using Ollama
- Privacy-focused architecture
- Java desktop application

## Technology Stack

- Java 17
- Java Swing
- Apache Maven
- Apache PDFBox
- Ollama
- Qwen3 4B
- HTTP Client API

## How It Works

```text
PDF Study Material
        |
        v
   PDF Extraction
        |
        v
   StudyLens AI
        |
        v
   Local AI Model
        |
        +----------------+
        |       |        |
        v       v        v
      Q&A   Summary   Key Topics
                         |
                         v
                       Quiz
