import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CinemaGUI extends JFrame {
    private List<Movie> movies;
    private JList<String> movieList;
    private JLabel posterlabel;
    private JTextArea infoArea;
    private Movie selectedMovie;

    public CinemaGUI() {
        setTitle("Добро");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // 1. Заполняем список фильмов
        initMovies();

        // 2. Слева — список названий (JList)
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Movie m : movies) {
            listModel.addElement(m.toString());
        }
        movieList = new JList<>(listModel);
        movieList.setFont(new Font("Arial", Font.PLAIN, 16));
        movieList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        movieList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JScrollPane listScroll = new JScrollPane(movieList);
        listScroll.setPreferredSize(new Dimension(260, 0));
        listScroll.setBorder(BorderFactory.createTitledBorder("Фильмы в прокате"));
        add(listScroll, BorderLayout.WEST);

        // 3. По центру — постер (JLabel)
        posterlabel = new JLabel("← Выбери фильм слева", SwingConstants.CENTER);
        posterlabel.setFont(new Font("Arial", Font.PLAIN, 18));
        posterlabel.setPreferredSize(new Dimension(400, 0));
        add(posterlabel, BorderLayout.CENTER);

        // 4. Справа — описание (JTextArea)
        infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoArea.setFont(new Font("Arial", Font.PLAIN, 14));
        infoArea.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane infoScroll = new JScrollPane(infoArea);
        infoScroll.setPreferredSize(new Dimension(300, 0));
        infoScroll.setBorder(BorderFactory.createTitledBorder("О фильме"));
        add(infoScroll, BorderLayout.EAST);

        // 5. Снизу — кнопка "Смотреть трейлер"
        JButton trailerButton = new JButton("  Смотреть трейлер");
        trailerButton.setFont(new Font("Arial", Font.BOLD, 15));
        trailerButton.setBackground(new Color(220, 50, 50));
        trailerButton.setForeground(Color.WHITE);
        trailerButton.setFocusPainted(false);
        trailerButton.addActionListener(e -> playTrailer());
        add(trailerButton, BorderLayout.SOUTH);

        // 6. Реакция на выбор фильма в списке
        movieList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int index = movieList.getSelectedIndex();
                if (index >= 0) {
                    selectedMovie = movies.get(index);
                    showMovieInfo(selectedMovie);
                }
            }
        });

        setVisible(true);
    }

    // Создание списка фильмов (постеры и видео лежат в папке media/)
    private void initMovies() {
        movies = new ArrayList<>();

        movies.add(new Movie(
                "Миньоны и монстры",
                6,
                8,
                "Продолжение приключений жёлтых миньонов! На этот раз они попадают " +
                        "в мир странных существ и монстров. Анимационная комедия от студии Illumination " +
                        "в формате 3D. В прокате Cineplex с 9 июля 2026 года.",
                "media/Minionii.jpg",
                "media/minionii.mp4",
                "Illumination Studios"
        ));

        movies.add(new Movie(
                "Человек-паук: Новый день",
                12,
                9,
                "Питер Паркер начинает новую главу своей жизни в качестве Человека-паука. " +
                        "Ему предстоит столкнуться с новыми врагами и переосмыслить, что значит быть " +
                        "героем. В прокате Cineplex с 6 августа 2026 года в формате 3D.",
                "media/SpiderManBrandNewDay.jpg",
                "media/spiderman.mp4",
                "Marvel Studios"
        ));

        movies.add(new Movie(
                "Одиссея",
                16,
                10,
                "Эпическая экранизация древнегреческого мифа от Кристофера Нолана. " +
                        "В ролях: Мэтт Дэймон, Том Холланд, Энн Хэтэуэй, Роберт Паттинсон, " +
                        "Люпита Нионго, Зендая, Шарлиз Терон. Полностью снят на камеры IMAX. " +
                        "В кинотеатрах Cineplex с 23 июля 2026 года.",
                "media/TheOdyssey.jpg",
                "media/odiseea.mp4",
                "Christopher Nolan"
        ));
    }

    // Показать постер и описание выбранного фильма
    private void showMovieInfo(Movie movie) {
        ImageIcon icon = new ImageIcon(movie.getPosterPath());
        if (icon.getIconWidth() > 0) {
            Image img = icon.getImage().getScaledInstance(380, 550, Image.SCALE_SMOOTH);
            posterlabel.setIcon(new ImageIcon(img));
            posterlabel.setText(null);
        } else {
            posterlabel.setIcon(null);
            posterlabel.setText("<html><center>Постер не найден:<br>" +
                    movie.getPosterPath() + "</center></html>");
        }

        infoArea.setText(
                "🎬 " + movie.getName() + "\n" +
                        "─────────────────────────\n\n" +
                        "Режиссёр / студия:\n" + movie.getDirector() + "\n\n" +
                        "Возрастное ограничение:  " + movie.getAge() + "+\n\n" +
                        "Рейтинг:  " + movie.getRaiting() + " / 10\n\n" +
                        "─────────────────────────\n" +
                        "Описание:\n\n" + movie.getDescription()
        );
        infoArea.setCaretPosition(0);
    }

    // Открыть видео-трейлер в системном плеере
    private void playTrailer() {
        if (selectedMovie == null) {
            JOptionPane.showMessageDialog(this,
                    "Сначала выбери фильм из списка слева",
                    "Внимание", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            File videoFile = new File(selectedMovie.getVideoPath());
            if (videoFile.exists()) {
                Desktop.getDesktop().open(videoFile);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Файл трейлера не найден:\n" + selectedMovie.getVideoPath() +
                                "\n\nПроверь, что видео лежит в папке media/",
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Не удалось открыть трейлер:\n" + ex.getMessage(),
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}