import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class Main {

    static String extractedText = "";

    static final String OLLAMA_URL =
            "http://localhost:11434/api/generate";

    static final String MODEL =
            "qwen3:4b";

    public static void main(String[] args) {

        JFrame frame = new JFrame("StudyLens AI");

        frame.setSize(850, 620);

        frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        frame.setLocationRelativeTo(null);

        // ================= BUTTONS =================

        JButton uploadButton =
                new JButton("Upload PDF");

        JButton askButton =
                new JButton("Ask AI");

        JButton summaryButton =
                new JButton("Summarize Notes");

        JButton topicsButton =
                new JButton("Key Topics");

        JButton quizButton =
                new JButton("Generate Quiz");

        // ================= MAIN PANEL =================

        JPanel panel = new JPanel();

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        50,
                        30,
                        50
                )
        );

        panel.setBackground(
                new Color(245, 247, 250)
        );

        // ================= TITLE =================

        JLabel title =
                new JLabel(
                        "StudyLens AI",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        36
                )
        );

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        // ================= SUBTITLE =================

        JLabel subtitle =
                new JLabel(
                        "Your Personal AI Study Assistant",
                        SwingConstants.CENTER
                );

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        17
                )
        );

        subtitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        // ================= STATUS =================

        JLabel status =
                new JLabel(
                        "Private - Local AI - Study Smarter",
                        SwingConstants.CENTER
                );

        status.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        status.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        // ================= FILE STATUS =================

        JLabel fileStatus =
                new JLabel(
                        "No PDF loaded",
                        SwingConstants.CENTER
                );

        fileStatus.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        fileStatus.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        // ================= BUTTON STYLE =================

        styleButton(uploadButton);
        styleButton(askButton);
        styleButton(summaryButton);
        styleButton(topicsButton);
        styleButton(quizButton);

        // ================= ADD TO PANEL =================

        panel.add(title);

        panel.add(
                Box.createVerticalStrut(8)
        );

        panel.add(subtitle);

        panel.add(
                Box.createVerticalStrut(5)
        );

        panel.add(status);

        panel.add(
                Box.createVerticalStrut(5)
        );

        panel.add(fileStatus);

        panel.add(
                Box.createVerticalStrut(30)
        );

        // Upload

        panel.add(
                centeredButton(
                        uploadButton,
                        650,
                        55
                )
        );

        panel.add(
                Box.createVerticalStrut(15)
        );

        // Ask + Summary

        JPanel row1 =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                15,
                                0
                        )
                );

        row1.setOpaque(false);

        row1.setMaximumSize(
                new Dimension(
                        650,
                        55
                )
        );

        row1.add(askButton);
        row1.add(summaryButton);

        panel.add(row1);

        panel.add(
                Box.createVerticalStrut(15)
        );

        // Topics + Quiz

        JPanel row2 =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                15,
                                0
                        )
                );

        row2.setOpaque(false);

        row2.setMaximumSize(
                new Dimension(
                        650,
                        55
                )
        );

        row2.add(topicsButton);
        row2.add(quizButton);

        panel.add(row2);

        panel.add(
                Box.createVerticalGlue()
        );

        // ================= FOOTER =================

        JLabel footer =
                new JLabel(
                        "Powered by local AI",
                        SwingConstants.CENTER
                );

        footer.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        footer.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        panel.add(footer);

        frame.add(panel);

        frame.setVisible(true);

        // =====================================================
        // UPLOAD PDF
        // =====================================================

        uploadButton.addActionListener(e -> {

            JFileChooser chooser =
                    new JFileChooser();

            int result =
                    chooser.showOpenDialog(frame);

            if (
                    result !=
                            JFileChooser.APPROVE_OPTION
            ) {
                return;
            }

            File file =
                    chooser.getSelectedFile();

            try {

                PDDocument document =
                        Loader.loadPDF(file);

                PDFTextStripper stripper =
                        new PDFTextStripper();

                extractedText =
                        stripper.getText(
                                document
                        );

                document.close();

                fileStatus.setText(
                        "Loaded: "
                                + file.getName()
                );

                JTextArea textArea =
                        new JTextArea(
                                extractedText
                        );

                textArea.setLineWrap(true);

                textArea.setWrapStyleWord(true);

                textArea.setEditable(false);

                textArea.setFont(
                        new Font(
                                "Arial",
                                Font.PLAIN,
                                15
                        )
                );

                JScrollPane scrollPane =
                        new JScrollPane(
                                textArea
                        );

                JFrame textFrame =
                        new JFrame(
                                "PDF Content"
                        );

                textFrame.setSize(
                        700,
                        500
                );

                textFrame.add(
                        scrollPane
                );

                textFrame.setLocationRelativeTo(
                        frame
                );

                textFrame.setVisible(true);

                JOptionPane.showMessageDialog(
                        frame,
                        "PDF loaded successfully!"
                );

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Error reading PDF:\n"
                                + ex.getMessage()
                );
            }
        });

        // =====================================================
        // ASK AI
        // =====================================================

        askButton.addActionListener(e -> {

            if (
                    extractedText.isEmpty()
            ) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Please upload a PDF first."
                );

                return;
            }

            String question =
                    JOptionPane.showInputDialog(
                            frame,
                            "What do you want to ask?"
                    );

            if (
                    question == null
                            ||
                    question.trim().isEmpty()
            ) {
                return;
            }

            runAI(
                    frame,

                    "AI is thinking...",

                    "StudyLens AI - Answer",

                    "Answer this question using "
                            + "the study notes.\n"
                            + "Keep the answer clear and concise.\n\n"
                            + "QUESTION:\n"
                            + question,

                    8000
            );
        });

        // =====================================================
        // SUMMARY
        // =====================================================

        summaryButton.addActionListener(e -> {

            if (
                    extractedText.isEmpty()
            ) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Please upload a PDF first."
                );

                return;
            }

            runAI(
                    frame,

                    "AI is summarizing your notes...",

                    "StudyLens AI - Summary",

                    "Summarize these study notes.\n"
                            + "Use simple language.\n"
                            + "Use clear headings and bullet points.\n"
                            + "Keep important concepts, "
                            + "definitions and examples.",

                    8000
            );
        });

        // =====================================================
        // KEY TOPICS
        // =====================================================

        topicsButton.addActionListener(e -> {

            if (
                    extractedText.isEmpty()
            ) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Please upload a PDF first."
                );

                return;
            }

            runAI(
                    frame,

                    "AI is finding important topics...",

                    "StudyLens AI - Key Topics",

                    "Analyze these study notes and "
                            + "identify the most important "
                            + "topics for studying.\n\n"

                            + "For each topic provide:\n"
                            + "1. Topic name\n"
                            + "2. Short explanation\n"
                            + "3. Important points\n\n"

                            + "Organize the answer using "
                            + "clear headings and bullet points.\n"

                            + "Focus only on information "
                            + "present in the study notes.",

                    8000
            );
        });

        // =====================================================
        // QUIZ
        // =====================================================

        quizButton.addActionListener(e -> {

            if (
                    extractedText.isEmpty()
            ) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Please upload a PDF first."
                );

                return;
            }

            generateInteractiveQuiz(
                    frame
            );
        });
    }

    // =====================================================
    // BUTTON STYLE
    // =====================================================

    public static void styleButton(
            JButton button) {

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        button.setFocusPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );
    }

    // =====================================================
    // CENTERED BUTTON
    // =====================================================

    public static JButton centeredButton(
            JButton button,
            int width,
            int height) {

        button.setMaximumSize(
                new Dimension(
                        width,
                        height
                )
        );

        button.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        return button;
    }

    // =====================================================
    // GENERATE QUIZ
    // =====================================================

    public static void generateInteractiveQuiz(
            JFrame parent) {

        JDialog loading =
                new JDialog(
                        parent,
                        "StudyLens AI",
                        true
                );

        JLabel label =
                new JLabel(
                        "AI is creating your quiz...",
                        SwingConstants.CENTER
                );

        label.setBorder(
                BorderFactory.createEmptyBorder(
                        25,
                        40,
                        25,
                        40
                )
        );

        loading.add(label);

        loading.pack();

        loading.setLocationRelativeTo(
                parent
        );

        SwingWorker<String, Void> worker =
                new SwingWorker<>() {

                    @Override
                    protected String doInBackground() {

                        return askOllama(

                                "Create exactly 5 MCQs "
                                        + "from the study notes.\n\n"

                                        + "IMPORTANT FORMAT:\n"

                                        + "Q1: question\n"
                                        + "A: option\n"
                                        + "B: option\n"
                                        + "C: option\n"
                                        + "D: option\n"
                                        + "ANSWER: A\n\n"

                                        + "Q2: question\n"
                                        + "A: option\n"
                                        + "B: option\n"
                                        + "C: option\n"
                                        + "D: option\n"
                                        + "ANSWER: B\n\n"

                                        + "Continue the same "
                                        + "format until Q5.\n"

                                        + "Do not add explanations "
                                        + "or extra text.",

                                6000
                        );
                    }

                    @Override
                    protected void done() {

                        loading.dispose();

                        try {

                            String response =
                                    get();

                            ArrayList<QuizQuestion>
                                    questions =
                                    parseQuiz(
                                            response
                                    );

                            if (
                                    questions.size()
                                            < 5
                            ) {

                                JOptionPane.showMessageDialog(
                                        parent,

                                        "AI generated an "
                                                + "unexpected quiz format."
                                                + "\n\n"
                                                + response
                                );

                                return;
                            }

                            showInteractiveQuiz(
                                    parent,
                                    questions
                            );

                        } catch (Exception ex) {

                            JOptionPane.showMessageDialog(
                                    parent,
                                    "Quiz Error:\n"
                                            + ex.getMessage()
                            );
                        }
                    }
                };

        worker.execute();

        loading.setVisible(true);
    }

    // =====================================================
    // QUIZ QUESTION
    // =====================================================

    static class QuizQuestion {

        String question;

        String[] options;

        int correctAnswer;

        QuizQuestion(
                String question,
                String[] options,
                int correctAnswer) {

            this.question =
                    question;

            this.options =
                    options;

            this.correctAnswer =
                    correctAnswer;
        }
    }

    // =====================================================
    // PARSE QUIZ
    // =====================================================

    public static ArrayList<QuizQuestion>
    parseQuiz(
            String text) {

        ArrayList<QuizQuestion>
                list =
                new ArrayList<>();

        Pattern pattern =
                Pattern.compile(

                        "Q\\d+\\s*:\\s*(.*?)\\s*"

                                + "A\\s*:\\s*(.*?)\\s*"

                                + "B\\s*:\\s*(.*?)\\s*"

                                + "C\\s*:\\s*(.*?)\\s*"

                                + "D\\s*:\\s*(.*?)\\s*"

                                + "ANSWER\\s*:\\s*([ABCD])",

                        Pattern.CASE_INSENSITIVE
                                | Pattern.DOTALL
                );

        Matcher matcher =
                pattern.matcher(
                        text
                );

        while (
                matcher.find()
        ) {

            String question =
                    cleanText(
                            matcher.group(1)
                    );

            String[] options = {

                    cleanText(
                            matcher.group(2)
                    ),

                    cleanText(
                            matcher.group(3)
                    ),

                    cleanText(
                            matcher.group(4)
                    ),

                    cleanText(
                            matcher.group(5)
                    )
            };

            String answer =
                    matcher.group(6)
                            .toUpperCase();

            int correct =
                    answer.charAt(0)
                            - 'A';

            list.add(
                    new QuizQuestion(
                            question,
                            options,
                            correct
                    )
            );

            if (
                    list.size()
                            == 5
            ) {
                break;
            }
        }

        return list;
    }

    // =====================================================
    // CLEAN TEXT
    // =====================================================

    public static String cleanText(
            String text) {

        return text

                .replace(
                        "\n",
                        " "
                )

                .replace(
                        "\r",
                        " "
                )

                .trim();
    }

    // =====================================================
    // INTERACTIVE QUIZ
    // =====================================================

    public static void showInteractiveQuiz(
            JFrame parent,
            ArrayList<QuizQuestion>
                    questions) {

        JFrame quizFrame =
                new JFrame(
                        "StudyLens AI - Interactive Quiz"
                );

        quizFrame.setSize(
                750,
                550
        );

        quizFrame.setLocationRelativeTo(
                parent
        );

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(
                                15,
                                15
                        )
                );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        JLabel questionLabel =
                new JLabel();

        questionLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );

        JPanel optionsPanel =
                new JPanel(
                        new GridLayout(
                                4,
                                1,
                                10,
                                10
                        )
                );

        ButtonGroup group =
                new ButtonGroup();

        JRadioButton[] options =
                new JRadioButton[4];

        for (
                int i = 0;
                i < 4;
                i++
        ) {

            options[i] =
                    new JRadioButton();

            options[i].setFont(
                    new Font(
                            "Arial",
                            Font.PLAIN,
                            16
                    )
            );

            group.add(
                    options[i]
            );

            optionsPanel.add(
                    options[i]
            );
        }

        JButton submitButton =
                new JButton(
                        "Submit Answer"
                );

        JLabel progressLabel =
                new JLabel(
                        "Question 1 of 5",
                        SwingConstants.CENTER
                );

        JPanel bottomPanel =
                new JPanel(
                        new BorderLayout()
                );

        bottomPanel.add(
                progressLabel,
                BorderLayout.NORTH
        );

        bottomPanel.add(
                submitButton,
                BorderLayout.SOUTH
        );

        mainPanel.add(
                questionLabel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                optionsPanel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        quizFrame.add(
                mainPanel
        );

        final int[] currentQuestion =
                {0};

        final int[] score =
                {0};

        final boolean[] answered =
                {false};

        Runnable displayQuestion =
                () -> {

                    QuizQuestion q =
                            questions.get(
                                    currentQuestion[0]
                            );

                    questionLabel.setText(
                            "<html>Question "
                                    + (
                                    currentQuestion[0]
                                            + 1
                            )
                                    + ": "
                                    + q.question
                                    + "</html>"
                    );

                    for (
                            int i = 0;
                            i < 4;
                            i++
                    ) {

                        options[i].setText(
                                (char) (
                                        'A' + i
                                )
                                        + ") "
                                        + q.options[i]
                        );

                        options[i].setSelected(
                                false
                        );

                        options[i].setEnabled(
                                true
                        );
                    }

                    progressLabel.setText(
                            "Question "
                                    + (
                                    currentQuestion[0]
                                            + 1
                            )
                                    + " of "
                                    + questions.size()
                    );

                    submitButton.setText(
                            "Submit Answer"
                    );

                    answered[0] =
                            false;

                    group.clearSelection();
                };

        displayQuestion.run();

        submitButton.addActionListener(e -> {

            if (
                    !answered[0]
            ) {

                int selected =
                        -1;

                for (
                        int i = 0;
                        i < 4;
                        i++
                ) {

                    if (
                            options[i]
                                    .isSelected()
                    ) {

                        selected =
                                i;

                        break;
                    }
                }

                if (
                        selected == -1
                ) {

                    JOptionPane.showMessageDialog(
                            quizFrame,
                            "Please select an option."
                    );

                    return;
                }

                QuizQuestion q =
                        questions.get(
                                currentQuestion[0]
                        );

                if (
                        selected
                                ==
                        q.correctAnswer
                ) {

                    score[0]++;

                    JOptionPane.showMessageDialog(
                            quizFrame,
                            "Correct!"
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            quizFrame,

                            "Wrong!\n"
                                    + "Correct answer: "
                                    + (
                                    char
                                            ) (
                                    'A'
                                            + q.correctAnswer
                            )
                    );
                }

                for (
                        int i = 0;
                        i < 4;
                        i++
                ) {

                    options[i].setEnabled(
                            false
                    );
                }

                answered[0] =
                        true;

                if (
                        currentQuestion[0]
                                ==
                        questions.size()
                                - 1
                ) {

                    submitButton.setText(
                            "Show Result"
                    );

                } else {

                    submitButton.setText(
                            "Next Question"
                    );
                }

            } else {

                if (
                        currentQuestion[0]
                                ==
                        questions.size()
                                - 1
                ) {

                    JOptionPane.showMessageDialog(
                            quizFrame,

                            "Quiz Completed!\n\n"
                                    + "Your Score: "
                                    + score[0]
                                    + " / "
                                    + questions.size()
                    );

                    quizFrame.dispose();

                } else {

                    currentQuestion[0]++;

                    displayQuestion.run();
                }
            }
        });

        quizFrame.setVisible(true);
    }

    // =====================================================
    // RUN AI IN BACKGROUND
    // =====================================================

    public static void runAI(
            JFrame parent,
            String loadingMessage,
            String resultTitle,
            String instruction,
            int maxCharacters) {

        JDialog loading =
                new JDialog(
                        parent,
                        "StudyLens AI",
                        true
                );

        JLabel label =
                new JLabel(
                        loadingMessage,
                        SwingConstants.CENTER
                );

        label.setBorder(
                BorderFactory.createEmptyBorder(
                        25,
                        40,
                        25,
                        40
                )
        );

        loading.add(label);

        loading.pack();

        loading.setLocationRelativeTo(
                parent
        );

        SwingWorker<String, Void>
                worker =
                new SwingWorker<>() {

                    @Override
                    protected String
                    doInBackground() {

                        return askOllama(
                                instruction,
                                maxCharacters
                        );
                    }

                    @Override
                    protected void
                    done() {

                        loading.dispose();

                        try {

                            showResult(
                                    parent,
                                    resultTitle,
                                    get()
                            );

                        } catch (
                                Exception ex
                        ) {

                            JOptionPane.showMessageDialog(
                                    parent,

                                    "AI Error:\n"
                                            + ex.getMessage()
                            );
                        }
                    }
                };

        worker.execute();

        loading.setVisible(true);
    }

    // =====================================================
    // OLLAMA
    // =====================================================

    public static String askOllama(
            String instruction,
            int maxCharacters) {

        try {

            String notes =
                    extractedText;

            if (
                    notes.length()
                            > maxCharacters
            ) {

                notes =
                        notes.substring(
                                0,
                                maxCharacters
                        );
            }

            String prompt =
                    "You are StudyLens AI, "
                            + "a study assistant.\n\n"
                            + instruction
                            + "\n\n"
                            + "STUDY NOTES:\n"
                            + notes;

            String json =
                    "{"
                            + "\"model\":\""
                            + MODEL
                            + "\","
                            + "\"prompt\":\""
                            + escapeJson(prompt)
                            + "\","
                            + "\"stream\":false"
                            + "}";

            HttpClient client =
                    HttpClient.newHttpClient();

            HttpRequest request =
                    HttpRequest.newBuilder()

                            .uri(
                                    URI.create(
                                            OLLAMA_URL
                                    )
                            )

                            .header(
                                    "Content-Type",
                                    "application/json"
                            )

                            .POST(
                                    HttpRequest
                                            .BodyPublishers
                                            .ofString(
                                                    json
                                            )
                            )

                            .build();

            HttpResponse<String>
                    response =
                    client.send(
                            request,
                            HttpResponse
                                    .BodyHandlers
                                    .ofString()
                    );

            if (
                    response.statusCode()
                            != 200
            ) {

                return "Ollama Error: "
                        + response.statusCode()
                        + "\n"
                        + response.body();
            }

            return extractResponse(
                    response.body()
            );

        } catch (
                Exception ex
        ) {

            return "Could not connect to Ollama.\n\n"
                    + ex.getMessage();
        }
    }

    // =====================================================
    // JSON ESCAPE
    // =====================================================

    public static String escapeJson(
            String text) {

        return text

                .replace(
                        "\\",
                        "\\\\"
                )

                .replace(
                        "\"",
                        "\\\""
                )

                .replace(
                        "\n",
                        "\\n"
                )

                .replace(
                        "\r",
                        "\\r"
                )

                .replace(
                        "\t",
                        "\\t"
                );
    }

    // =====================================================
    // EXTRACT RESPONSE
    // =====================================================

    public static String extractResponse(
            String json) {

        String key =
                "\"response\":\"";

        int start =
                json.indexOf(
                        key
                );

        if (
                start == -1
        ) {

            return json;
        }

        start +=
                key.length();

        int end =
                start;

        boolean escaped =
                false;

        while (
                end < json.length()
        ) {

            char c =
                    json.charAt(end);

            if (
                    c == '"'
                            &&
                    !escaped
            ) {

                break;
            }

            if (
                    c == '\\'
                            &&
                    !escaped
            ) {

                escaped =
                        true;

            } else {

                escaped =
                        false;
            }

            end++;
        }

        return json
                .substring(
                        start,
                        end
                )

                .replace(
                        "\\n",
                        "\n"
                )

                .replace(
                        "\\\"",
                        "\""
                )

                .replace(
                        "\\\\",
                        "\\"
                );
    }

    // =====================================================
    // SHOW RESULT
    // =====================================================

    public static void showResult(
            JFrame parent,
            String title,
            String result) {

        JTextArea area =
                new JTextArea(
                        result
                );

        area.setLineWrap(true);

        area.setWrapStyleWord(true);

        area.setEditable(false);

        area.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        16
                )
        );

        JScrollPane scroll =
                new JScrollPane(
                        area
                );

        scroll.setVerticalScrollBarPolicy(
                JScrollPane
                        .VERTICAL_SCROLLBAR_ALWAYS
        );

        JFrame resultFrame =
                new JFrame(
                        title
                );

        resultFrame.setSize(
                750,
                550
        );

        resultFrame.add(
                scroll
        );

        resultFrame.setLocationRelativeTo(
                parent
        );

        resultFrame.setVisible(true);
    }
}