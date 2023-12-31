PGDMP                         {            pizzaria    14.8    14.8 )    %           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            &           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            '           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            (           1262    125072    pizzaria    DATABASE     h   CREATE DATABASE pizzaria WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Portuguese_Brazil.1252';
    DROP DATABASE pizzaria;
                aluno    false            �            1259    125073    address    TABLE     .  CREATE TABLE public.address (
    id bigint NOT NULL,
    cep character varying(255),
    city character varying(255) NOT NULL,
    neighborhood character varying(255) NOT NULL,
    number character varying(255),
    state character varying(255) NOT NULL,
    street character varying(255) NOT NULL
);
    DROP TABLE public.address;
       public         heap    aluno    false            �            1259    125118    address_seq    SEQUENCE     t   CREATE SEQUENCE public.address_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.address_seq;
       public          aluno    false            �            1259    125080    client    TABLE     )  CREATE TABLE public.client (
    id bigint NOT NULL,
    cpf character varying(255),
    email character varying(255),
    logincode character varying(255),
    name character varying(255) NOT NULL,
    phone character varying(255) NOT NULL,
    voucher double precision,
    address_id bigint
);
    DROP TABLE public.client;
       public         heap    aluno    false            �            1259    125119 
   client_seq    SEQUENCE     s   CREATE SEQUENCE public.client_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 !   DROP SEQUENCE public.client_seq;
       public          aluno    false            �            1259    125087    employee    TABLE     �   CREATE TABLE public.employee (
    id bigint NOT NULL,
    login character varying(255),
    name character varying(255),
    password character varying(255),
    type character varying(255)
);
    DROP TABLE public.employee;
       public         heap    aluno    false            �            1259    125120    employee_seq    SEQUENCE     u   CREATE SEQUENCE public.employee_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.employee_seq;
       public          aluno    false            �            1259    125094    item    TABLE       CREATE TABLE public.item (
    id bigint NOT NULL,
    delivered boolean NOT NULL,
    quantity integer,
    totalcost double precision,
    unitprice double precision,
    product_id bigint,
    request_id bigint,
    CONSTRAINT item_quantity_check CHECK ((quantity >= 1))
);
    DROP TABLE public.item;
       public         heap    aluno    false            �            1259    125121    item_seq    SEQUENCE     q   CREATE SEQUENCE public.item_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    DROP SEQUENCE public.item_seq;
       public          aluno    false            �            1259    125100    product    TABLE       CREATE TABLE public.product (
    id bigint NOT NULL,
    available boolean NOT NULL,
    category character varying(255),
    description character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    price double precision,
    size character varying(255)
);
    DROP TABLE public.product;
       public         heap    aluno    false            �            1259    125122    product_seq    SEQUENCE     t   CREATE SEQUENCE public.product_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.product_seq;
       public          aluno    false            �            1259    125107    request    TABLE     �   CREATE TABLE public.request (
    id bigint NOT NULL,
    date date,
    open boolean NOT NULL,
    tablenumber integer NOT NULL,
    value double precision,
    voucher boolean NOT NULL,
    client bigint
);
    DROP TABLE public.request;
       public         heap    aluno    false            �            1259    125123    request_seq    SEQUENCE     t   CREATE SEQUENCE public.request_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.request_seq;
       public          aluno    false                      0    125073    address 
   TABLE DATA           U   COPY public.address (id, cep, city, neighborhood, number, state, street) FROM stdin;
    public          aluno    false    209   �.                 0    125080    client 
   TABLE DATA           ]   COPY public.client (id, cpf, email, logincode, name, phone, voucher, address_id) FROM stdin;
    public          aluno    false    210   �.                 0    125087    employee 
   TABLE DATA           C   COPY public.employee (id, login, name, password, type) FROM stdin;
    public          aluno    false    211   �.                 0    125094    item 
   TABLE DATA           e   COPY public.item (id, delivered, quantity, totalcost, unitprice, product_id, request_id) FROM stdin;
    public          aluno    false    212   /                 0    125100    product 
   TABLE DATA           Z   COPY public.product (id, available, category, description, name, price, size) FROM stdin;
    public          aluno    false    213   5/                 0    125107    request 
   TABLE DATA           V   COPY public.request (id, date, open, tablenumber, value, voucher, client) FROM stdin;
    public          aluno    false    214   u2       )           0    0    address_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.address_seq', 1, false);
          public          aluno    false    215            *           0    0 
   client_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.client_seq', 1, false);
          public          aluno    false    216            +           0    0    employee_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.employee_seq', 2, true);
          public          aluno    false    217            ,           0    0    item_seq    SEQUENCE SET     7   SELECT pg_catalog.setval('public.item_seq', 1, false);
          public          aluno    false    218            -           0    0    product_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.product_seq', 45, true);
          public          aluno    false    219            .           0    0    request_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.request_seq', 1, false);
          public          aluno    false    220            w           2606    125079    address address_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.address
    ADD CONSTRAINT address_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.address DROP CONSTRAINT address_pkey;
       public            aluno    false    209            y           2606    125086    client client_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.client DROP CONSTRAINT client_pkey;
       public            aluno    false    210                       2606    125093    employee employee_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.employee
    ADD CONSTRAINT employee_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.employee DROP CONSTRAINT employee_pkey;
       public            aluno    false    211            �           2606    125099    item item_pkey 
   CONSTRAINT     L   ALTER TABLE ONLY public.item
    ADD CONSTRAINT item_pkey PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.item DROP CONSTRAINT item_pkey;
       public            aluno    false    212            �           2606    125106    product product_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.product DROP CONSTRAINT product_pkey;
       public            aluno    false    213            �           2606    125111    request request_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.request
    ADD CONSTRAINT request_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.request DROP CONSTRAINT request_pkey;
       public            aluno    false    214            {           2606    125115 #   client uk_bfgjs3fem0hmjhvih80158x29 
   CONSTRAINT     _   ALTER TABLE ONLY public.client
    ADD CONSTRAINT uk_bfgjs3fem0hmjhvih80158x29 UNIQUE (email);
 M   ALTER TABLE ONLY public.client DROP CONSTRAINT uk_bfgjs3fem0hmjhvih80158x29;
       public            aluno    false    210            }           2606    125113 #   client uk_plnstg0h55xcbwkkf8iehxo71 
   CONSTRAINT     ]   ALTER TABLE ONLY public.client
    ADD CONSTRAINT uk_plnstg0h55xcbwkkf8iehxo71 UNIQUE (cpf);
 M   ALTER TABLE ONLY public.client DROP CONSTRAINT uk_plnstg0h55xcbwkkf8iehxo71;
       public            aluno    false    210            �           2606    125117 #   product ukaqxsaqj95gvwhha106al94x46 
   CONSTRAINT     {   ALTER TABLE ONLY public.product
    ADD CONSTRAINT ukaqxsaqj95gvwhha106al94x46 UNIQUE (name, description, size, category);
 M   ALTER TABLE ONLY public.product DROP CONSTRAINT ukaqxsaqj95gvwhha106al94x46;
       public            aluno    false    213    213    213    213            �           2606    125134     item fk2228qqf5ddxrwgjx3ukn8d3bi    FK CONSTRAINT     �   ALTER TABLE ONLY public.item
    ADD CONSTRAINT fk2228qqf5ddxrwgjx3ukn8d3bi FOREIGN KEY (request_id) REFERENCES public.request(id);
 J   ALTER TABLE ONLY public.item DROP CONSTRAINT fk2228qqf5ddxrwgjx3ukn8d3bi;
       public          aluno    false    3207    212    214            �           2606    125139 #   request fk9pp2omb1it5b8mfdw1ar17qp9    FK CONSTRAINT     �   ALTER TABLE ONLY public.request
    ADD CONSTRAINT fk9pp2omb1it5b8mfdw1ar17qp9 FOREIGN KEY (client) REFERENCES public.client(id);
 M   ALTER TABLE ONLY public.request DROP CONSTRAINT fk9pp2omb1it5b8mfdw1ar17qp9;
       public          aluno    false    214    3193    210            �           2606    125124 "   client fkhhghy9llr4gn6jqdfmby8b674    FK CONSTRAINT     �   ALTER TABLE ONLY public.client
    ADD CONSTRAINT fkhhghy9llr4gn6jqdfmby8b674 FOREIGN KEY (address_id) REFERENCES public.address(id);
 L   ALTER TABLE ONLY public.client DROP CONSTRAINT fkhhghy9llr4gn6jqdfmby8b674;
       public          aluno    false    210    209    3191            �           2606    125129     item fkqm7g91d532ttjxv4n0lhlnhkf    FK CONSTRAINT     �   ALTER TABLE ONLY public.item
    ADD CONSTRAINT fkqm7g91d532ttjxv4n0lhlnhkf FOREIGN KEY (product_id) REFERENCES public.product(id);
 J   ALTER TABLE ONLY public.item DROP CONSTRAINT fkqm7g91d532ttjxv4n0lhlnhkf;
       public          aluno    false    3203    213    212                  x������ � �            x������ � �         =   x�3�LL����t���%E�)�E01_O?.#���4N���̼��̢|(��Ս+F��� ��            x������ � �         0  x��V�n�@]�������%!$��4�>ԨRt1d{ܱ���(�����[�X��0�(MD BB`�=��I�^����>__���^�Ecɲ{ >04�"	yp�ɐ����R2��>/�x����R᧯��	'�DA �p��C�IA�|r�����k^���mt:��]J�A�bʂ�%1��(���K�jcv���`sM�`~v���R2����������L��׾S?�2f�{��Xa$#��#x^�w:���!N|����l�w�8��Z�#	U��g�����A�4�ƽs�y�EFK���w>�����c���
��Mt�CwT���igC�G��YH����2�nR-dU�10������j�_06�!e�걞|d%�q����!���5O�\� �|�e�HN�͇o��G�Q|A�YS0�Av�5���	��1�r�"�t�<�**N��W��1�È��E!�KUN4I W��`�췈�ٗ��������:i�ʎ�75Ց�-�`s佹i��rwn�#����\ߑu$��7=�b��I�a زުL��������@�i��Z���{C�AEo�?t2G�ۇ��w�;�y��G;���jk�q�гm�?�9t [�[5���{���TOz���)�fH��	΂�p����S��Z�������&��σ��y�T��-iDiv+R���Y�.���!������M���M���n���^]E�T�~i�B���);!�/�ܒZ�C��8U;bv��g�c�������d�N�jQ�S�C��
��˻��            x������ � �     