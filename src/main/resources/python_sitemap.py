#!/usr/bin/env python
# -*- coding:utf-8 -*-

import io
import re
import time
import requests
from lxml import etree
from bs4 import BeautifulSoup

XML_HEADER = u'<?xml version="1.0" encoding="UTF-8"?>\n'
URL = 'http://www.szyoy.com'
URL_LIST = {}
OPENED_URL = set()


def open_url(url):
    if url not in OPENED_URL:
        response = requests.get(url)
        response.encoding = 'utf-8'
        soup = BeautifulSoup(response.text, 'html.parser')
        all_a = soup.find_all('a')
        urls = format_a(all_a)
        url_list = {}
        for url in urls:
            if foreign_chain(url):
                url_list[url] = url
                URL_LIST[url] = url
        OPENED_URL.add(url)
        return url_list
    else:
        return None


def format_a(url_elements):
    urls = set()
    for url in url_elements:
        href = url.get('href')
        if href and href.startswith('/'):
            href = URL + href
        if href:
            # delete query param or anchor
            start = href.find('#')
            if start > 0:
                href = href[0:start]
            start = href.find('?')
            if start > 0:
                href = href[0:start]
            urls.add(href)
    return urls


def foreign_chain(url):
    return url.find(URL) == 0


def generate_xml(filename, url_list):
    """
    generate a new xml file use url_list
    :param filename:
    :param url_list:
    :return:
    """
    root = etree.Element('urlset', xmlns="http://www.sitemaps.org/schemas/sitemap/0.9")
    for each in url_list:
        url = etree.Element('url')
        loc = etree.Element('loc')
        priority = etree.Element('priority')
        changefreq = etree.Element('changefreq')
        lastmod = etree.Element('lastmod')
        loc.text = each
        if each == URL or each == URL + '/':
            priority.text = '1.0'
        else:
            priority.text = '0.8'
        changefreq.text = 'always'
        lastmod.text = get_current_date()
        url.append(loc)
        url.append(priority)
        url.append(lastmod)
        url.append(changefreq)
        if each == URL or each == URL + '/':
            root.insert(0, url)
        else:
            root.append(url)
    s = etree.tostring(root, encoding='utf-8', pretty_print=True)
    with io.open(filename, 'w', encoding='utf-8') as f:
        f.write(unicode(XML_HEADER + s))


def update_xml(filename, url_list):
    """
    Add new url_list to origin xml file
    :param filename:
    :param url_list:
    :return:
    """
    with io.open(filename, 'r', encoding='utf-8') as f:
        lines = [i.strip() for i in f.readlines()]
    old_url_list = []
    for each_line in lines:
        d = re.findall('<loc>(http:\/\/.+)<\/loc>', each_line)
        old_url_list + d
    url_list += old_url_list
    generate_xml(filename, url_list)


def generate_xml_index(filename, sitemap_list, lastmod_list):
    """
    Generate sitemap index xml file
    :param filename:
    :param sitemap_list:
    :param lastmod_list:
    :return:
    """
    root = etree.Element('sitemapindex', xmlns="http://www.sitemaps.org/schemas/sitemap/0.9")
    for each_sitemap, each_lastmod in zip(sitemap_list, lastmod_list):
        sitemap = etree.Element('sitemap')
        loc = etree.Element('loc')
        loc.text = each_sitemap
        lastmod = etree.Element('lastmod')
        lastmod.text = each_lastmod
        sitemap.append(loc)
        sitemap.append(lastmod)
        root.append(sitemap)
    s = etree.tostring(root, encoding='utf-8', pretty_print=True)
    with io.open(filename, 'w', encoding='utf-8') as f:
        f.write(unicode(XML_HEADER + s))


def get_current_date():
    time_stamp = time.time()
    t = time.localtime(time_stamp)
    return time.strftime('%Y-%m-%d', t)


if __name__ == '__main__':
    home_all_url = open_url(URL)
    if isinstance(home_all_url, dict):
        for home_url in home_all_url:
            if foreign_chain(home_url):
                open_url(home_url)
    generate_xml('sitemap.xml', URL_LIST.values())
    print('python generate sitemap.xml success.')
