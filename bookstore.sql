-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- 主机： localhost
-- 生成日期： 2021-05-16 01:14:07
-- 服务器版本： 5.6.45-log
-- PHP 版本： 7.3.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `bookstore`
--

-- --------------------------------------------------------

--
-- 表的结构 `book`
--

CREATE TABLE `book` (
  `id` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `author` varchar(50) DEFAULT NULL,
  `isbn` varchar(20) DEFAULT NULL,
  `price` decimal(10,2) NOT NULL,
  `stock` int(11) NOT NULL,
  `type` varchar(20) DEFAULT NULL,
  `cover` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `book`
--

INSERT INTO `book` (`id`, `title`, `author`, `isbn`, `price`, `stock`, `type`, `cover`) VALUES
(1, '深入理解计算机系统', '兰德尔·E·布莱恩特', '9787111544937', '136.90', 0, '计算机科学', 'http://img3m7.ddimg.cn/48/0/24106647-1_w_6.jpg'),
(2, '动物庄园', '乔治·奥威尔', '9787231547939', '20.40', 99, '外国文学', 'http://img3m1.ddimg.cn/82/3/25229341-1_w_2.jpg'),
(3, '悲惨世界（上中下）（精装版）', '雨果', '91809701899529', '104.00', 499, '外国文学', 'http://img3m7.ddimg.cn/13/15/27912667-1_u_1.jpg'),
(4, '哈利·波特与魔法石', 'J·K·罗琳', '97496264895879', '30.20', 998, '外国文学', 'http://img3m1.ddimg.cn/88/0/25479421-1_w_1.jpg'),
(5, '哈利·波特与密室', 'J·K·罗琳', '9787020144549', '33.30', 499, '外国文学', 'http://img3m1.ddimg.cn/68/17/25479401-1_w_1.jpg'),
(6, '重启咲良田1 猫、幽灵和星期天的革命', '河野裕', '9787550022317', '18.74', 299, '外国文学', 'http://img3m9.ddimg.cn/53/35/1775318939-1_w_1.jpg');

-- --------------------------------------------------------

--
-- 表的结构 `order`
--

CREATE TABLE `order` (
  `id` int(11) NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` int(11) NOT NULL,
  `total` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `order`
--

INSERT INTO `order` (`id`, `time`, `user_id`, `total`) VALUES
(13, '2021-05-15 17:13:25', 1, '18.74'),
(14, '2021-05-15 17:13:43', 1, '53.70');

-- --------------------------------------------------------

--
-- 表的结构 `order_book`
--

CREATE TABLE `order_book` (
  `order_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `amount` int(11) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `order_book`
--

INSERT INTO `order_book` (`order_id`, `book_id`, `amount`) VALUES
(13, 6, 1),
(14, 2, 1),
(14, 5, 1);

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(256) NOT NULL,
  `disabled` tinyint(1) NOT NULL DEFAULT '0',
  `role` varchar(10) NOT NULL DEFAULT 'ROLE_USER'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `user`
--

INSERT INTO `user` (`id`, `username`, `email`, `password`, `disabled`, `role`) VALUES
(1, 'admin', 'i@gpx.moe', '$2a$10$ZWJHlK37Ikb6aVAMSCg8Oe3dLoa6aPwAIi5axfrEpFHONk.XP4dni', 0, 'ROLE_ADMIN'),
(2, 'Googleplex', 'i@gpx.moe', '$2a$10$l7bmlkEE5G/8oSEdavS9a.Fpfz860crrh5o9ZZc7NS3ZyKqG/ltk6', 0, 'ROLE_USER');

--
-- 转储表的索引
--

--
-- 表的索引 `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `isbn` (`isbn`);

--
-- 表的索引 `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- 表的索引 `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `book`
--
ALTER TABLE `book`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- 使用表AUTO_INCREMENT `order`
--
ALTER TABLE `order`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- 使用表AUTO_INCREMENT `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- 限制导出的表
--

--
-- 限制表 `order`
--
ALTER TABLE `order`
  ADD CONSTRAINT `order_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- 限制表 `order_book`
--
ALTER TABLE `order_book`
  ADD CONSTRAINT `order_book_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
  ADD CONSTRAINT `order_book_ibfk_2` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
