--
-- PostgreSQL database dump
--

-- Dumped from database version 12.3
-- Dumped by pg_dump version 12.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: t_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.t_role (
    id integer NOT NULL,
    role_name character varying(50) NOT NULL,
    role_card character varying(100) NOT NULL
);


ALTER TABLE public.t_role OWNER TO postgres;

--
-- Name: t_role_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.t_role_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.t_role_id_seq OWNER TO postgres;

--
-- Name: t_role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.t_role_id_seq OWNED BY public.t_role.id;


--
-- Name: t_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.t_user (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    phone character varying(15) NOT NULL,
    username character varying(50) NOT NULL,
    password character varying(200) NOT NULL,
    age integer DEFAULT 1 NOT NULL
);


ALTER TABLE public.t_user OWNER TO postgres;

--
-- Name: t_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.t_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.t_user_id_seq OWNER TO postgres;

--
-- Name: t_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.t_user_id_seq OWNED BY public.t_user.id;


--
-- Name: t_user_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.t_user_role (
    id integer NOT NULL,
    users_id bigint NOT NULL,
    role_id bigint NOT NULL
);


ALTER TABLE public.t_user_role OWNER TO postgres;

--
-- Name: t_user_role_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.t_user_role_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.t_user_role_id_seq OWNER TO postgres;

--
-- Name: t_user_role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.t_user_role_id_seq OWNED BY public.t_user_role.id;


--
-- Name: t_role id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_role ALTER COLUMN id SET DEFAULT nextval('public.t_role_id_seq'::regclass);


--
-- Name: t_user id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_user ALTER COLUMN id SET DEFAULT nextval('public.t_user_id_seq'::regclass);


--
-- Name: t_user_role id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_user_role ALTER COLUMN id SET DEFAULT nextval('public.t_user_role_id_seq'::regclass);


--
-- Data for Name: t_role; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.t_role (id, role_name, role_card) FROM stdin;
1	ROLE_MEMBER	ROLE_MEMBER
2	ROLE_STOCK	ROLE_STOCK
\.


--
-- Data for Name: t_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.t_user (id, name, phone, username, password, age) FROM stdin;
1	admin	13844441234	admin	$2a$10$yjdybUK1yK7/xmLg1Z46QuBc4.Yb9vQXYnW6nHi48yXQxRNbJ5TDa	20
\.


--
-- Data for Name: t_user_role; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.t_user_role (id, users_id, role_id) FROM stdin;
2	1	1
3	1	2
\.


--
-- Name: t_role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.t_role_id_seq', 2, true);


--
-- Name: t_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.t_user_id_seq', 1, true);


--
-- Name: t_user_role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.t_user_role_id_seq', 3, true);


--
-- Name: t_role t_role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_role
    ADD CONSTRAINT t_role_pkey PRIMARY KEY (id);


--
-- Name: t_user t_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_user
    ADD CONSTRAINT t_user_pkey PRIMARY KEY (id);


--
-- Name: t_user_role t_user_role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_user_role
    ADD CONSTRAINT t_user_role_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

